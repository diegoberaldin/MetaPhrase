# MetaPhrase
An open source translation editor specifically designed for native mobile apps (Android and iOS).

<img width="1138" alt="translation_editor" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/0820c13a-145a-4f57-9988-f16cb1396ac1">


After creating a project (and selecting the relevant languages, each project must have at least one language and exactly one base language), the application allows to import Android XML resource files and/or iOS stringtables.
It makes easier to manage translations since the base message is always displayed in local versions as a reference, and it is updated if the base version is changed at any time.

After translation is complete, the files can be exported backwards to native formats (XML or .strings) for each language. The placeholders, for which the standard is the Android format in the editor, are converted back to iOS "@" conventions.

This is still under heavy development, filter and search features are yet to come as well as placeholder validation.
