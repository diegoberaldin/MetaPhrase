
## Architecture

### Module structure

The code is organized in a series of isolated modules that can be grouped into three categories in hierarchical order (inspired by Robert Martin's Clean architecture):

- **core modules**: common utilities that provided the low level infrastructure and are shared among higher modules. Core modules names conventionally start with the `core-` prefix.
- **domain modules**: business logic (repositories and use cases), in the most complex cases these modules also contain data definitions and persistence classes (entities and data sources). Domain modules are organized by domain specificity (project, translation memory, glossary, etc.) and their name starts with the `domain-` prefix.
- **feature modules**: top-level modules that contain the presentation and UI layer for the different sections of the application (project, translation editor, panels, dialogs, etc.). They are easily identified because name starts with the `feature-` prefix.

Each module can have multiple submodules, especially complex ones (such as domain or feature modules). Domain modules for example tend to have a submodule for repositories and a submodule for use cases; they can optionally have a data and or persistence submodule. Feature modules can have a dialog submodule (with each dialog as a sub-submodule) or can have different submodules modeling different UI parts (e.g. the translation toolbar or the message list in the feature-translate module).

If a module requires dependency injection, the bindings for the classes and interfaces of that particular module are contained in a `di` package within that same module.

Here ist a short description of what can be found in each module:

- **core-common** contains a set shared utilities divided by package: coroutine dispatchers (coroutines), file system (files), data store (keystore), logging (log), notification center (notification), shared UI components and theme (ui), extension functions and utilities (utils).
- **core-localization**: contains the main entry point to localization in the `L10n` shared object and String extension functions; `L10n` uses the internal `DefaultLocalization` class internally to manage the language bundles. 
- **core-persistence**: contains the `AppDatabase` class that provides a centralized entry point for the persistence layer and it is a factory for the DAO classes (which are found in each domain persistence submodule). Whenever a new persisted entity is created, `AppDatabase` needs to be updated for the schema creation/update and with the create DAO factory method.
- **domain-formats** contains the business logic (mainly usecases) to manage import and export to resource files (Android XML, iOS stringtables, Windows resx and GNU gettext PO)
- **domain-glossary** contains the data layer and business logic layers of the glossary feature; it is divided into the following submodules:
  - **data** contains the model classes for the glossary terms
  - **persistence** contains the entity definitions and local data source (DAO, data access object) for glossary terms and associations between terms
  - **repository** contains the repositories to create, read, update and delete glossary terms and associations between terms
  - **usecase** contains the use cases needed to perform the glossary lookup operations
- **domain-project** contains the data layer and business logic layer for project, languages and messages (segments and segment pairs, aka translation units); it is divided in the following submodules:
  - **data** contains the model classes for project, languages, segments and translation units
  - **persistence** contains the entity definitions and local data sources for project data
  - **repository** contains the repositories to create, read, update and delete project data
  - **usecase** contains the use cases needed to interact with project data
- **domain-spellcheck** contains the business logic for the spelling checker and sentence analyzer. The entry point for this feature is the `ValidateSpellingUseCase` class for checking a sentence for errors (and to get suggestions); whereas stemming is accessible via the `Spelling#getLemmata(String)` method.
- **domain-tm** contains the data layer and business logic layer for the translation memory; it is divided in the following submodules:
  - **data** contains the model classes translation memory entries
  - **persistence** contains the entity definitions and local data sources for translation memory entries
  - **repository** contains the repositories to create, read, update and delete entries from the translation memory
  - **usecase** contains the use cases needed to check for similarities, importing exporting and managing the translation memory
- **feature-intro** contains the presentation logic and UI for the intro screen (empty project screen)
- **feature-main** contains the presentation logic and UI for the root content, that routes the user either towards the intro screen or the projects content (either project list or translation editor); this module also contains the **dialog:settings** submodule for the application settings dialog.
- **feature-projects** contains the presentation logic that routes the user either towards the project list or the translate content; it additionally has the following submodules:
  - **list** presentation logic and UI for project list screen
  - **dialog:newproject** contains the presentation logic and UI for the project creation and edit dialog
  - **dialog:statistics** contains the presentation logic and UI for the project statistics dialog
- **feature-translate** is the main module which contains the presentation logic for the translation editor, panels and dialog; it has the following submodules:
  - **dialog:newsegment** contains the presentation logic and UI for the message creation dialog
  - **dialog:newterm** contains the presentation logic and UI for the new glossary term dialog
  - **messages** contains the presentation logic and UI for the message list inside the translation editor
  - **panel:glossary** contains the presentation logic and UI for the glossary panel
  - **panel:matches** contains the presentation logic and UI for the TM matches panel
  - **panel:memory** contains the presentation logic and UI for the TM content panel
  - **panel:validate** contains the presentation logic and UI for the validation panel
  - **toolbar** contains the presentation logic and UI for the translate toolbar
