
## Architecture

### Module structure

The code is organized in a series of isolated modules that can be grouped into three categories in hierarchical order (inspired by Robert Martin's Clean architecture):

- **core modules**: common utilities that provided the low level infrastructure and are shared among higher modules. Core modules names conventionally start with the `core-` prefix.
- **domain modules**: business logic (repositories and use cases), in the most complex cases these modules also contain data definitions and persistence classes (entities and data sources). Domain modules are organized by domain specificity (project, translation memory, glossary, etc.) and their name starts with the `domain-` prefix.
- **feature modules**: top-level modules that contain the presentation and UI layer for the different sections of the application (project, translation editor, panels, dialogs, etc.). They are easily identified because name starts with the `feature-` prefix.

Each module can have multiple submodules, especially complex ones (such as domain or feature modules). Domain modules for example tend to have a submodule for repositories and a submodule for use cases; they can optionally have a data and or persistence submodule. Feature modules can have a dialog submodule (with each dialog as a sub-submodule) or can have different submodules modeling different UI parts (e.g. the translation toolbar or the message list in the feature-translate module).

If a module requires dependency injection, the bindings for the classes and interfaces of that particular module are contained in a `di` package within that same module.

Here ist a short description of what can be found in each module:

- **core-common**, contains a set shared utilities divided by package: coroutine dispatchers (coroutines), file system (files), data store (keystore), logging (log), notification center (notification), shared UI components and theme (ui), extension functions and utilities (utils).
- **core-localization**: contains the main entry point to localization in the L10n shared object and String extension functions
- **core-persistence**: contains the AppDatabase class that provides a centralized entry point for persistence and it is a factory for the DAO classes (which are found in each domain persistence submodule).