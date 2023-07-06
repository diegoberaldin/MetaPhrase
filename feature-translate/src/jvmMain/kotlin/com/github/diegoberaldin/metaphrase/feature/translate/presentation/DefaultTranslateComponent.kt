package com.github.diegoberaldin.metaphrase.feature.translate.presentation

import androidx.compose.runtime.snapshotFlow
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
import com.github.diegoberaldin.metaphrase.core.common.keystore.KeyStoreKeys
import com.github.diegoberaldin.metaphrase.core.common.keystore.TemporaryKeyStore
import com.github.diegoberaldin.metaphrase.core.common.notification.NotificationCenter
import com.github.diegoberaldin.metaphrase.core.common.utils.asFlow
import com.github.diegoberaldin.metaphrase.core.common.utils.getByInjection
import com.github.diegoberaldin.metaphrase.core.common.utils.lastPathSegment
import com.github.diegoberaldin.metaphrase.core.common.utils.stripExtension
import com.github.diegoberaldin.metaphrase.domain.formats.ExportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.formats.ImportResourcesUseCase
import com.github.diegoberaldin.metaphrase.domain.glossary.data.GlossaryTermModel
import com.github.diegoberaldin.metaphrase.domain.glossary.repository.GlossaryTermRepository
import com.github.diegoberaldin.metaphrase.domain.language.data.LanguageModel
import com.github.diegoberaldin.metaphrase.domain.language.repository.LanguageRepository
import com.github.diegoberaldin.metaphrase.domain.mt.repository.MachineTranslationRepository
import com.github.diegoberaldin.metaphrase.domain.project.data.RecentProjectModel
import com.github.diegoberaldin.metaphrase.domain.project.data.ResourceFileType
import com.github.diegoberaldin.metaphrase.domain.project.data.SegmentModel
import com.github.diegoberaldin.metaphrase.domain.project.repository.ProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.RecentProjectRepository
import com.github.diegoberaldin.metaphrase.domain.project.repository.SegmentRepository
import com.github.diegoberaldin.metaphrase.domain.project.usecase.ImportSegmentsUseCase
import com.github.diegoberaldin.metaphrase.domain.project.usecase.SaveProjectUseCase
import com.github.diegoberaldin.metaphrase.domain.project.usecase.ValidatePlaceholdersUseCase
import com.github.diegoberaldin.metaphrase.domain.spellcheck.usecase.ValidateSpellingUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.ExportTmxUseCase
import com.github.diegoberaldin.metaphrase.domain.tm.usecase.SyncProjectWithTmUseCase
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newsegment.presentation.NewSegmentComponent
import com.github.diegoberaldin.metaphrase.feature.translate.dialog.newterm.presentation.NewGlossaryTermComponent
import com.github.diegoberaldin.metaphrase.feature.translate.messages.presentation.MessageListComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.glossary.presentation.GlossaryComponent
import com.github.diegoberaldin.metaphrase.feature.translate.panel.machinetranslation.presentation.MachineTranslationComponent
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
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.coroutines.CoroutineContext
import kotlin.time.Duration

@OptIn(ExperimentalCoroutinesApi::class, FlowPreview::class)
internal class DefaultTranslateComponent(
    componentContext: ComponentContext,
    private val coroutineContext: CoroutineContext,
    private val dispatchers: CoroutineDispatcherProvider,
    private val projectRepository: ProjectRepository,
    private val recentProjectRepository: RecentProjectRepository,
    private val languageRepository: LanguageRepository,
    private val segmentRepository: SegmentRepository,
    private val glossaryTermRepository: GlossaryTermRepository,
    private val importResources: ImportResourcesUseCase,
    private val importSegments: ImportSegmentsUseCase,
    private val exportResources: ExportResourcesUseCase,
    private val validatePlaceholders: ValidatePlaceholdersUseCase,
    private val exportToTmx: ExportTmxUseCase,
    private val validateSpelling: ValidateSpellingUseCase,
    private val syncProjectWithTm: SyncProjectWithTmUseCase,
    private val saveProject: SaveProjectUseCase,
    private val machineTranslationRepository: MachineTranslationRepository,
    private val notificationCenter: NotificationCenter,
    private val keyStore: TemporaryKeyStore,
) : TranslateComponent, ComponentContext by componentContext {

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

    override val uiState = MutableStateFlow(TranslateUiState())
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

    override lateinit var currentLanguage: StateFlow<LanguageModel?>

    private suspend fun getCurrentLanguage(): LanguageModel? =
        toolbar.asFlow<TranslateToolbarComponent>().firstOrNull()?.uiState?.value?.currentLanguage

    init {
        with(lifecycle) {
            doOnCreate {
                viewModelScope = CoroutineScope(coroutineContext + SupervisorJob())
                currentLanguage = toolbar.asFlow<TranslateToolbarComponent>()
                    .flatMapConcat { it?.uiState ?: snapshotFlow { null } }.map { it?.currentLanguage }.stateIn(
                        scope = viewModelScope,
                        started = SharingStarted.WhileSubscribed(5_000),
                        initialValue = null,
                    )
                viewModelScope.launch {
                    configureToolbar()
                    configureMessageList()
                    configurePanel()
                }
                projectRepository.observeNeedsSaving().onEach { needsSaving ->
                    uiState.update { it.copy(needsSaving = needsSaving) }
                }.launchIn(viewModelScope)

                toolbarNavigation.activate(ToolbarConfig)
                messageListNavigation.activate(MessageListConfig)
                panelNavigation.activate(PanelConfig.None)
            }
            doOnStart {
                viewModelScope.launch(dispatchers.io) {
                    updateUnitCount()

                    projectRepository.observeById(projectId).onEach { proj ->
                        val currentProjectName = uiState.value.project?.name
                        if (proj.name != currentProjectName) {
                            uiState.update { it.copy(project = proj) }
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
            toolbarComponent.reduce(TranslateToolbarComponent.ViewIntent.SetEditing(isEditing))
            if (!isEditing) {
                panel.asFlow<TranslationMemoryComponent>().firstOrNull()?.clear()
                panel.asFlow<GlossaryComponent>().firstOrNull()?.clear()
                panel.asFlow<MachineTranslationComponent>().firstOrNull()?.clear()
            }
        }.launchIn(this)
        dialog.asFlow<NewSegmentComponent>(timeout = Duration.INFINITE).filterNotNull().onEach {
            it.effects.filterIsInstance<NewSegmentComponent.Effect.Done>().onEach { event ->
                withContext(dispatchers.main) {
                    dialogNavigation.activate(DialogConfig.None)
                }
                if (event.segment != null) {
                    projectRepository.setNeedsSaving(true)
                    updateUnitCount()
                    messageListComponent.reduce(MessageListComponent.ViewIntent.Refresh)
                }
            }.launchIn(this)
        }.launchIn(this)
        messageListComponent.editedSegment.filterNotNull().onEach { segment ->
            val key = segment.key
            panel.asFlow<TranslationMemoryComponent>().firstOrNull()?.load(
                key = key,
                projectId = projectId,
                languageId = getCurrentLanguage()?.id ?: 0,
            )
            panel.asFlow<GlossaryComponent>().firstOrNull()?.load(
                key = key,
                projectId = projectId,
                languageId = getCurrentLanguage()?.id ?: 0,
            )
            panel.asFlow<MachineTranslationComponent>().firstOrNull()?.load(
                key = key,
                projectId = projectId,
                languageId = getCurrentLanguage()?.id ?: 0,
            )
        }.launchIn(this)
        messageListComponent.effects.filterIsInstance<MessageListComponent.Effect.AddToGlossary>()
            .onEach { (lemma, lang) ->
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
                    panel.asFlow<GlossaryComponent>().firstOrNull()?.load(
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
                panel.asFlow<GlossaryComponent>().firstOrNull()?.load(
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
                messageListComponent.reduce(MessageListComponent.ViewIntent.SetEditingEnabled(false))
                messageListComponent.reduce(
                    MessageListComponent.ViewIntent.ReloadMessages(
                        language = language,
                        filter = filter,
                        projectId = projectId,
                    ),
                )
                messageListComponent.reduce(MessageListComponent.ViewIntent.SetEditingEnabled(true))
                // resets the current validation
                panel.asFlow<ValidateComponent>().firstOrNull()?.clear()
                // resets the TM
                panel.asFlow<BrowseMemoryComponent>().firstOrNull()?.setLanguages(
                    source = languageRepository.getBase(projectId),
                    target = getCurrentLanguage(),
                )
            }.launchIn(this)
        toolbarComponent.effects.onEach { evt ->
            when (evt) {
                TranslateToolbarComponent.Effect.MoveToPrevious -> {
                    moveToPrevious()
                }

                TranslateToolbarComponent.Effect.MoveToNext -> {
                    moveToNext()
                }

                is TranslateToolbarComponent.Effect.Search -> {
                    val searchText = evt.text
                    messageListComponent.reduce(MessageListComponent.ViewIntent.Search(searchText))
                }

                TranslateToolbarComponent.Effect.CopyBase -> {
                    copyBase()
                }

                TranslateToolbarComponent.Effect.AddUnit -> {
                    addSegment()
                }

                TranslateToolbarComponent.Effect.RemoveUnit -> {
                    deleteSegment()
                }

                TranslateToolbarComponent.Effect.ValidateUnits -> {
                    startPlaceholderValidation()
                }

                else -> Unit
            }
        }.launchIn(this)
    }

    private suspend fun CoroutineScope.configurePanel() {
        panel.asFlow<Any>(timeout = Duration.INFINITE).onEach { child ->
            when (child) {
                is TranslationMemoryComponent -> {
                    child.copyEvents.onEach { textToCopy ->
                        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
                        messageListComponent?.reduce(MessageListComponent.ViewIntent.ChangeSegmentText(textToCopy))
                    }.launchIn(this)
                }

                is ValidateComponent -> {
                    child.selectionEvents.onEach { key ->
                        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
                        messageListComponent?.reduce(MessageListComponent.ViewIntent.ScrollToMessage(key))
                    }.launchIn(this)
                }

                is BrowseMemoryComponent -> {
                    child.setLanguages(
                        source = languageRepository.getBase(projectId),
                        target = getCurrentLanguage(),
                    )
                }

                is MachineTranslationComponent -> {
                    child.copySourceEvents.onEach { textToCopy ->
                        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
                        messageListComponent?.reduce(MessageListComponent.ViewIntent.ChangeSegmentText(textToCopy))
                    }.launchIn(this)
                    child.copyTargetEvents.onEach {
                        val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
                        val segmentId = messageListComponent?.editedSegment?.value?.id
                        if (segmentId != null) {
                            val segment = segmentRepository.getById(segmentId)
                            val text = segment?.text.orEmpty()
                            child.copyTranslation(text)
                        }
                    }.launchIn(this)
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
            PanelConfig.MachineTranslation -> getByInjection<MachineTranslationComponent>(context, coroutineContext)
            else -> Unit
        }
    }

    private fun loadProject() {
        if (!this::viewModelScope.isInitialized) return

        viewModelScope.launch(dispatchers.io) {
            val proj = projectRepository.getById(projectId)
            uiState.update { it.copy(project = proj) }
            updateUnitCount()

            messageList.asFlow<MessageListComponent>().firstOrNull()?.apply {
                reduce(MessageListComponent.ViewIntent.ClearMessages)
                reduce(MessageListComponent.ViewIntent.Refresh)
            }
            toolbar.asFlow<TranslateToolbarComponent>().firstOrNull()?.apply {
                projectId = this@DefaultTranslateComponent.projectId
            }
        }
    }

    private suspend fun updateUnitCount() {
        val baseLanguage = languageRepository.getBase(projectId)
        if (baseLanguage != null) {
            val baseSegments = segmentRepository.getAll(baseLanguage.id)
            uiState.update { it.copy(unitCount = baseSegments.size) }
        }
    }

    override fun closeDialog() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(DialogConfig.None)
        }
    }

    override fun save(path: String) {
        viewModelScope.launch(dispatchers.io) {
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            saveProject(path = path, projectId = projectId)
            val project = projectRepository.getById(projectId)
            if (project != null) {
                val projectName = path.lastPathSegment().stripExtension()
                val existingRecent = recentProjectRepository.getByName(value = projectName)
                if (existingRecent == null && path.isNotEmpty()) {
                    recentProjectRepository.create(
                        model = RecentProjectModel(
                            path = path,
                            name = projectName,
                        ),
                    )
                }
            }
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
            projectRepository.setNeedsSaving(false)
        }
    }

    override fun import(path: String, type: ResourceFileType) {
        viewModelScope.launch(dispatchers.io) {
            val language = getCurrentLanguage() ?: return@launch
            projectRepository.setNeedsSaving(true)
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val messageListComponent = messageList.asFlow<MessageListComponent>().firstOrNull()
            messageListComponent?.reduce(MessageListComponent.ViewIntent.SetEditingEnabled(false))
            messageListComponent?.reduce(MessageListComponent.ViewIntent.ClearMessages)
            val segments = importResources(path = path, type = type)
            importSegments(
                segments = segments,
                language = language,
                projectId = projectId,
            )

            delay(100)
            updateUnitCount()
            messageListComponent?.reduce(MessageListComponent.ViewIntent.Refresh)
            messageListComponent?.reduce(MessageListComponent.ViewIntent.SetEditingEnabled(true))
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
            messageList.asFlow<MessageListComponent>().firstOrNull()
                ?.reduce(MessageListComponent.ViewIntent.MoveToPrevious)
        }
    }

    override fun moveToNext() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.reduce(MessageListComponent.ViewIntent.MoveToNext)
        }
    }

    override fun endEditing() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.reduce(MessageListComponent.ViewIntent.EndEditing)
        }
    }

    override fun copyBase() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()?.reduce(MessageListComponent.ViewIntent.CopyBase)
        }
    }

    override fun addSegment() {
        viewModelScope.launch(dispatchers.main) {
            dialogNavigation.activate(DialogConfig.NewSegment)
        }
    }

    override fun deleteSegment() {
        viewModelScope.launch(dispatchers.io) {
            messageList.asFlow<MessageListComponent>().firstOrNull()
                ?.reduce(MessageListComponent.ViewIntent.DeleteSegment)
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

                else -> Unit
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
                panel.asFlow<TranslationMemoryComponent>().firstOrNull()?.load(
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
                panel.asFlow<GlossaryComponent>().firstOrNull()?.load(
                    key = currentKey,
                    languageId = getCurrentLanguage()?.id ?: 0,
                    projectId = projectId,
                )
            }
        }
    }

    override fun tryLoadMachineTranslation() {
        viewModelScope.launch(dispatchers.io) {
            delay(100)
            val currentKey = messageList.asFlow<MessageListComponent>().firstOrNull()?.editedSegment?.value?.key
            if (currentKey != null) {
                panel.asFlow<MachineTranslationComponent>().firstOrNull()?.load(
                    key = currentKey,
                    languageId = getCurrentLanguage()?.id ?: 0,
                    projectId = projectId,
                )
            }
        }
    }

    override fun exportTmx(path: String) {
        viewModelScope.launch(dispatchers.io) {
            val baseLanguage = languageRepository.getBase(projectId) ?: return@launch
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            exportToTmx(path = path, sourceLang = baseLanguage.code)
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

    override fun machineTranslationRetrieve() {
        viewModelScope.launch(dispatchers.io) {
            panel.asFlow<MachineTranslationComponent>().firstOrNull()?.retrieve()
        }
    }

    override fun machineTranslationInsert() {
        viewModelScope.launch(dispatchers.io) {
            panel.asFlow<MachineTranslationComponent>().firstOrNull()?.insertTranslation()
        }
    }

    override fun machineTranslationCopyTarget() {
        viewModelScope.launch(dispatchers.io) {
            panel.asFlow<MachineTranslationComponent>().firstOrNull()?.copyTarget()
        }
    }

    override fun machineTranslationShare() {
        viewModelScope.launch(dispatchers.io) {
            panel.asFlow<MachineTranslationComponent>().firstOrNull()?.share()
        }
    }

    override fun machineTranslationContributeTm() {
        viewModelScope.launch(dispatchers.io) {
            val project = projectRepository.getById(projectId)
            val recentProjectModel = recentProjectRepository.getByName(project?.name.orEmpty())
            val file = recentProjectModel?.let { File(it.path) }.takeIf { it?.canRead() == true } ?: return@launch

            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = true))
            val key = keyStore.get(KeyStoreKeys.MachineTranslationKey, "").takeIf { it.isNotEmpty() }
            val provider = keyStore.get(KeyStoreKeys.MachineTranslationProvider, 0).let {
                MachineTranslationRepository.AVAILABLE_PROVIDERS[it]
            }
            machineTranslationRepository.importTm(
                provider = provider,
                key = key,
                file = file,
            )
            notificationCenter.send(NotificationCenter.Event.ShowProgress(visible = false))
        }
    }

    companion object {
        const val KEY_DIALOG_SLOT = "DialogSlot"
        const val KEY_TOOLBAR_SLOT = "ToolbarSlot"
        const val KEY_MESSAGE_LIST_SLOT = "MessageListSlot"
        const val KEY_PANEL_SLOT = "PanelSlot"
    }
}
