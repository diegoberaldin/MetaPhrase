package com.github.diegoberaldin.metaphrase.feature.translate.presentation

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.doOnCreate
import com.arkivanov.essenty.lifecycle.doOnDestroy
import com.arkivanov.essenty.lifecycle.doOnStart
import com.github.diegoberaldin.metaphrase.core.common.coroutines.CoroutineDispatcherProvider
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.core.common.utils.asFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.domain.formats.ExportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ImportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.ProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.ImportSegmentsUseCase
import com.github.diegoberaldin.metaphrase.domain.project.usecase.ValidatePlaceholdersUseCase
import com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase.ValidateSpellingUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ExportTmxUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.SyncProjectWithTmUseCase
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation.NewSegmentComponent
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation.NewGlossaryTermComponent
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.MessageListComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation.GlossaryComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.matches.presentation.TranslationMemoryComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.memory.presentation.BrowseMemoryComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.validate.presentation.ValidateComponent
import com.github.diegoberaldin.metaphrase.feature.translate.presentation.TranslateComponent.DialogConfig
import com.github.diegoberaldin.metaphrase.feature.translate.presentation.TranslateComponent.MessageListConfig
import com.github.diegoberaldin.metaphrase.feature.translate.presentation.TranslateComponent.PanelConfig
import com.github.diegoberaldin.metaphrase.feature.translate.presentation.TranslateComponent.ToolbarConfig
import com.github.diegoberaldin.metaphrase.feature.translate.toolbar.presentation.TranslateToolbarComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class)
internal class DefaultTranslateComponent(
    componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val projectRepository: ProjectRepository,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val importResources: ImportResourcesUseCase,
    private val importSegments: ImportSegmentsUseCase,
    private val exportResources: ExportResourcesUseCase,
    private val validatePlaceholders: ValidatePlaceholdersUseCase,
    private val notificationCenter: NotificationCenter,
    private val exportToTmx: ExportTmxUseCase,
    private val validateSpelling: ValidateSpellingUseCase,
    private val syncProjectWithTm: SyncProjectWithTmUseCase,
    private val glossaryTermRepository: GlossaryTermRepository,
) : TranslateComponent, ComponentContext by componentContext {

    private val project = MutableStateFlow<ProjectModel?>(null)
    private val unitCount = MutableStateFlow(0)

    private val toolbarNavigation = SlotNavigation<ToolbarConfig>()
    private val messageListNavigation = SlotNavigation<MessageListConfig>()
    private val dialogNavigation = SlotNavigation<DialogConfig>()
    private val panelNavigation = SlotNavigation<PanelConfig>()

    private lateinit var viewModelScope: CoroutineScope

    override var projectId: Int = 0
        set(value) {
            field = value
            loadProject()
        }

    override lateinit var uiState: StateFlow<TranslateUiState>
    override val toolbar: Value<ChildSlot<ToolbarConfig, TranslateToolbarComponent>> = childSlot(
        source = toolbarNavigation,
        key = KEY_TOOLBAR_SLOT,
        childFactory = { _, context ->
            getByInjection<TranslateToolbarComponent>(context, coroutineContext)
        },
    )
    override val messageList: Value<ChildSlot<MessageListConfig, MessageListComponent>> = childSlot(
        source = messageListNavigation,
        key = KEY_MESSAGE_LIST_SLOT,
        childFactory = { _, context ->
            getByInjection<MessageListComponent>(context, coroutineContext)
        },
    )
    override val panel: Value<ChildSlot<PanelConfig, *>> = childSlot(
        source = panelNavigation,
        key = KEY_PANEL_SLOT,
        childFactory = ::createPanelComponent,
    )
    override val dialog: Value<ChildSlot<DialogConfig, *>> = childSlot(
        source = dialogNavigation,
        key = KEY_DIALOG_SLOT,
        childFactory = ::createDialogComponent,
    )

    @OptIn(ExperimentalCoroutinesApi::class)
    override val isEditing: StateFlow<Boolean>
        get() = messageList.asFlow<MessageListComponent>().filterNotNull().flatMapLatest { it.editedSegment }
            .map { it != null }
            .stateIn(viewModelScope, initialValue = false, started = SharingStarted.WhileSubscribed(5000))

    override val currentLanguage: StateFlow<LanguageModel?>
        get() {
            return toolbar.asFlow<TranslateToolbarComponent>().filterNotNull().flatMapLatest { it.currentLanguage }
                .stateIn(viewModelScope, initialValue = null, started = SharingStarted.WhileSubscribed(5000))
        }

    private suspend fun getCurrentLanguage(): LanguageModel? =
        toolbar.asFlow<TranslateToolbarComponent>().firstOrNull()?.currentLanguage?.value

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                // done here to reinitialize flow due to sharing started policy
                uiState = combine(
                    project,
                    unitCount,
                ) { project, unitCount ->
                    TranslateUiState(
                        project = project,
                        unitCount = unitCount,
                    )
                }.stateIn(
                    scope = viewModelScope,
                    started = SharingStarted.WhileSubscribed(5_000),
                    initialValue = TranslateUiState(),
                )

                viewModelScope.launch {
                    configureToolbar()
                    configureMessageList()
                    configurePanel()
                }

                toolbarNavigation.activate(ToolbarConfig)
                messageListNavigation.activate(MessageListConfig)
                panelNavigation.activate(PanelConfig.None)
            }
            doOnStart {
                viewModelScope.launch(dispatchers.io) {
                    updateUnitCount()

                    projectRepository.observeById(projectId).onEach {
                        if (it.name != project.value?.name) {
                            project.value = it
                        }
                    }.launchIn(this)
                }
            }
            doOnDestroy {
                viewModelScope.cancel()
            }
        }
    }

    private suspend fun CoroutineScope.configureMessageList() {
        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull() ?: return
        val toolbarComponent = toolbar.asFlow<TranslateToolbarComponent>().firstOrNull() ?: return
        messageListComponent.editedSegment.onEach { segment ->
            val isEditing = segment != null
            toolbarComponent.setEditing(isEditing)
            if (!isEditing) {
                panel.asFlow<TranslationMemoryComponent>().firstOrNull()?.clear()
                panel.asFlow<GlossaryComponent>().firstOrNull()?.clear()
            }
        }.launchIn(this)
        dialog.asFlow<NewSegmentComponent>().filterNotNull().onEach {
            it.done.onEach { newSegment ->
                withContext(dispatchers.main) {
                    dialogNavigation.activate(DialogConfig.None)
                }
                if (newSegment != null) {
                    updateUnitCount()
                    messageListComponent.refresh()
                }
            }.launchIn(this)
        }.launchIn(this)
        messageListComponent.editedSegment.filterNotNull().onEach { segment ->
            val key = segment.key
            panel.asFlow<TranslationMemoryComponent>().firstOrNull()?.loadSimilarities(
                key = key,
                projectId = projectId,
                languageId = getCurrentLanguage()?.id ?: 0,
            )
            panel.asFlow<GlossaryComponent>().firstOrNull()?.loadGlossaryTerms(
                key = key,
                projectId = projectId,
                languageId = getCurrentLanguage()?.id ?: 0,
            )
        }.launchIn(this)
        messageListComponent.addToGlossaryEvents.onEach { (lemma, lang) ->
            val baseLanguage = languageRepository.getBase(projectId) ?: return@onEach
            if (baseLanguage.code == lang) {
                // for base language, add immediately
                val termModel = GlossaryTermModel(
                    lemma = lemma.lowercase(),
                    lang = lang,
                )
                val existing = glossaryTermRepository.get(lemma, lang)
                if (existing == null) {
                    glossaryTermRepository.create(termModel)
                }
            } else {
                // insert variant in dialog
                viewModelScope.launch(dispatchers.main) {
                    dialogNavigation.activate(DialogConfig.NewGlossaryTerm(target = lemma.lowercase()))
                }
            }
            // reload panel
            val key = messageListComponent.editedSegment.value?.key
            if (!key.isNullOrEmpty()) {
                panel.asFlow<GlossaryComponent>().firstOrNull()?.loadGlossaryTerms(
                    key = key,
                    projectId = projectId,
                    languageId = getCurrentLanguage()?.id ?: 0,
                )
            }
        }.launchIn(this)
    }

    override fun addGlossaryTerm(source: String?, target: String?) {
        val sourceTerm = source?.lowercase() ?: return
        val targetTerm = target?.lowercase() ?: return
        viewModelScope.launch(dispatchers.io) {
            val baseLanguage = languageRepository.getBase(projectId) ?: return@launch
            val targetLanguage = getCurrentLanguage() ?: return@launch

            // add source
            val sourceTermId: Int
            val sourceToAdd = GlossaryTermModel(
                lemma = sourceTerm.lowercase(),
                lang = baseLanguage.code,
            )
            val existingSource = glossaryTermRepository.get(sourceToAdd.lemma, sourceToAdd.lang)
            sourceTermId = existingSource?.id ?: glossaryTermRepository.create(sourceToAdd)

            // add target
            val targetTermId: Int
            val targetToAdd = GlossaryTermModel(
                lemma = targetTerm.lowercase(),
                lang = targetLanguage.code,
            )
            val existingTarget = glossaryTermRepository.get(targetToAdd.lemma, targetToAdd.lang)
            targetTermId = existingTarget?.id ?: glossaryTermRepository.create(targetToAdd)

            // create the association
            glossaryTermRepository.associate(sourceId = sourceTermId, targetId = targetTermId)

            // reload panel
            val key = messageList.asFlow<MessageListComponent>().firstOrNull()?.editedSegment?.value?.key
            if (!key.isNullOrEmpty()) {
                panel.asFlow<GlossaryComponent>().firstOrNull()?.loadGlossaryTerms(
                    key = key,
                    projectId = projectId,
                    languageId = getCurrentLanguage()?.id ?: 0,
                )
            }
        }
    }

    private suspend fun CoroutineScope.configureToolbar() {
        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull() ?: return
        val toolbarComponent = toolbar.asFlow<TranslateToolbarComponent>().firstOrNull() ?: return
        toolbarComponent.projectId = projectId
        toolbarComponent.uiState.mapLatest { it.currentLanguage to it.currentTypeFilter }.distinctUntilChanged()
            .onEach { (language, filter) ->
                if (language == null) return@onEach
                messageListComponent.setEditingEnabled(false)
                messageListComponent.reloadMessages(
                    language = language,
                    filter = filter,
                    projectId = projectId,
                )
                messageListComponent.setEditingEnabled(true)
                // resets the current validation
                panel.asFlow<ValidateComponent>().firstOrNull()?.clear()
                // resets the TM
                panel.asFlow<BrowseMemoryComponent>().firstOrNull()?.setLanguages(
                    source = languageRepository.getBase(projectId),
                    target = getCurrentLanguage(),
                )
            }.launchIn(this)
        toolbarComponent.events.onEach { evt ->
            when (evt) {
                TranslateToolbarComponent.Events.MoveToPrevious -> {
                    moveToPrevious()
                }

                TranslateToolbarComponent.Events.MoveToNext -> {
                    moveToNext()
                }

                is TranslateToolbarComponent.Events.Search -> {
                    val searchText = evt.text
                    messageListComponent.search(searchText)
                }

                TranslateToolbarComponent.Events.CopyBase -> {
                    copyBase()
                }

                TranslateToolbarComponent.Events.AddUnit -> {
                    addSegment()
                }

                TranslateToolbarComponent.Events.RemoveUnit -> {
                    deleteSegment()
                }

                TranslateToolbarComponent.Events.ValidateUnits -> {
                    startPlaceholderValidation()
                }
            }
        }.launchIn(this)
    }

    private suspend fun CoroutineScope.configurePanel() {
        panel.asFlow<Any>(timeout = Duration.INFINITE).onEach { child ->
            when (child) {
                is TranslationMemoryComponent -> {
                    child.copyEvents.onEach { textToCopy ->
                        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
                        messageListComponent?.changeSegmentText(textToCopy)
                    }.launchIn(this)
                }

                is ValidateComponent -> {
                    child.selectionEvents.onEach { key ->
                        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
                        messageListComponent?.scrollToMessage(key)
                    }.launchIn(this)
                }

                is BrowseMemoryComponent -> {
                    child.setLanguages(
                        source = languageRepository.getBase(projectId),
                        target = getCurrentLanguage(),
                    )
                }
            }
        }.launchIn(this)
    }

    private fun createDialogComponent(config: DialogConfig, context: ComponentContext): Any {
        return when (config) {
            DialogConfig.NewSegment -> getByInjection<NewSegmentComponent>(context, coroutineContext)
            is DialogConfig.NewGlossaryTerm -> getByInjection<NewGlossaryTermComponent>(context, coroutineContext)
            else -> Unit
        }
    }

    private fun createPanelComponent(config: PanelConfig, context: ComponentContext): Any {
        return when (config) {
            PanelConfig.Matches -> getByInjection<TranslationMemoryComponent>(context, coroutineContext)
            PanelConfig.Validation -> getByInjection<ValidateComponent>(context, coroutineContext)
            PanelConfig.MemoryContent -> getByInjection<BrowseMemoryComponent>(context, coroutineContext)
            PanelConfig.Glossary -> getByInjection<GlossaryComponent>(context, coroutineContext)
            else -> Unit
        }
    }

    private fun loadProject() {
        if (!this::viewModelScope.isInitialized) return

        viewModelScope.launch(dispatchers.io) {
            val proj = projectRepository.getById(projectId)
            project.value = proj
            updateUnitCount()
        }
    }

    private suspend fun updateUnitCount() {
        val baseLanguage = languageRepository.getBase(projectId)
        if (baseLanguage != null) {
            val baseSegments = segmentRepository.getAll(baseLanguage.id)
            unitCount.value = baseSegments.size
        }
    }

    override fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(DialogConfig.None)
        }
    }

    override fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            val language = getCurrentLanguage() ?: return@launch
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
            messageListComponent?.setEditingEnabled(false)
            messageListComponent?.clearMessages()
            val segments = importResources(path = path, type = type)
            importSegments(
                segments = segments,
                language = language,
                projectId = projectId,
            )

            delay(100)
            updateUnitCount()
            messageListComponent?.refresh()
            messageListComponent?.setEditingEnabled(true)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun export(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            val language = getCurrentLanguage() ?: return@launch
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val segments = segmentRepository.getAll(language.id).let {
                when (type) {
                    // replicate untranslatable segments
                    ResourceFileType.IOS_STRINGS,
                    ResourceFileType.RESX,
                    ResourceFileType.PO,
                    -> {
                        val baseLanguage = languageRepository.getBase(projectId)
                        if (baseLanguage != null) {
                            val untranslatable = segmentRepository.getUntranslatable(baseLanguage.id)
                            it + untranslatable
                        } else {
                            it
                        }
                    }

                    else -> it
                }
            }
            exportResources(segments = segments, path = path, lang = language.code, type = type)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun moveToPrevious() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.moveToPrevious()
        }
    }

    override fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.moveToNext()
        }
    }

    override fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.endEditing()
        }
    }

    override fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.copyBase()
        }
    }

    override fun addSegment() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(DialogConfig.NewSegment)
        }
    }

    override fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.deleteSegment()
        }
    }

    private fun startPlaceholderValidation() {
        viewModelScope.launch(dispatchers.io) {
            val language = getCurrentLanguage() ?: return@launch
            val baseLanguage = languageRepository.getBase(projectId) ?: return@launch
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val segmentPairs = segmentRepository.getAll(language.id).map {
                val key = it.key
                val original = segmentRepository.getByKey(key = key, languageId = baseLanguage.id) ?: SegmentModel()
                it to original
            }
            val result = validatePlaceholders(segmentPairs)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))

            withContext(dispatchers.main) {
                panelNavigation.activate(PanelConfig.Validation)
            }

            val child = panel.asFlow<ValidateComponent>().firstOrNull()
            when (result) {
                ValidatePlaceholdersUseCase.Output.Valid -> {
                    child?.loadInvalidPlaceholders(projectId, language.id, invalidKeys = emptyList())
                }

                is ValidatePlaceholdersUseCase.Output.Invalid -> {
                    child?.loadInvalidPlaceholders(projectId, language.id, invalidKeys = result.keys)
                }
            }
        }
    }

    private fun startSpellcheck() {
        viewModelScope.launch(dispatchers.io) {
            val language = getCurrentLanguage() ?: return@launch
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val items = segmentRepository.getAll(language.id)
                .map { ValidateSpellingUseCase.InputItem(key = it.key, message = it.text) }
            val errorMap = validateSpelling(input = items, lang = language.code)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))

            withContext(dispatchers.main) {
                panelNavigation.activate(PanelConfig.Validation)
            }

            val child = panel.asFlow<ValidateComponent>().firstOrNull()
            child?.loadSpellingMistakes(errorMap)
        }
    }

    override fun togglePanel(config: PanelConfig) {
        when (panel.value.child?.configuration) {
            config -> panelNavigation.activate(PanelConfig.None)
            else -> panelNavigation.activate(config)
        }
    }

    override fun tryLoadSimilarities() {
        viewModelScope.launch(dispatchers.io) {
            delay(100)
            val currentKey = messageList.asFlow<MessageListComponent>().firstOrNull()?.editedSegment?.value?.key
            if (currentKey != null) {
                panel.asFlow<TranslationMemoryComponent>().firstOrNull()?.loadSimilarities(
                    key = currentKey,
                    languageId = getCurrentLanguage()?.id ?: 0,
                    projectId = projectId,
                )
            }
        }
    }

    override fun tryLoadGlossary() {
        viewModelScope.launch(dispatchers.io) {
            delay(100)
            val currentKey = messageList.asFlow<MessageListComponent>().firstOrNull()?.editedSegment?.value?.key
            if (currentKey != null) {
                panel.asFlow<GlossaryComponent>().firstOrNull()?.loadGlossaryTerms(
                    key = currentKey,
                    languageId = getCurrentLanguage()?.id ?: 0,
                    projectId = projectId,
                )
            }
        }
    }

    override fun exportTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            exportToTmx(path = path, projectId = projectId)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun syncWithTm() {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            syncProjectWithTm(projectId = projectId)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    override fun validatePlaceholders() {
        startPlaceholderValidation()
    }

    override fun insertBestMatch() {
        viewModelScope.launch(dispatchers.io) {
            panel.asFlow<TranslationMemoryComponent>().firstOrNull()?.copyTranslation(0)
        }
    }

    override fun globalSpellcheck() {
        startSpellcheck()
    }

    companion object {
        const val KEY_DIALOG_SLOT = "DialogSlot"
        const val KEY_TOOLBAR_SLOT = "ToolbarSlot"
        const val KEY_MESSAGE_LIST_SLOT = "MessageListSlot"
        const val KEY_PANEL_SLOT = "PanelSlot"
    }
}
