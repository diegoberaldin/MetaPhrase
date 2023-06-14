
## Architecture

### Module structure

The code is organized in a series of isolated modules that can be grouped into three categories in hierarchical orders (vaguely inspired by Robert Martin's Clean architecture):

- **core modules**: they contain common utilities that provided the low level infrastructure and are shared among higher level modules
- **domain modules**: they contain business logic (repositories and use cases) and in the most complex cases they also contain data definitions, persistence classes (entities and data sources), organized by specific sector or domain (project, translation memory, glossary, etc.)
- **feature modules**: higher order modules that contain the presentation and UI layer for the different sections of the application (project, translation editor, panels, dialogs, etc.)