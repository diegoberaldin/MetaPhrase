## Import

After opening a new project, before starting the translation process, messages have to be imported, at least for the base language. Messages can be imported by using the "Project" > "Import" menu item.

Here is the list of supported file formats for import:
- Android XML resource (`strings.xml`)
- iOS stringtables (`Localizable.strings`)
- Windows resources (`Resources.resx`)
- GNU gettext PO files (`messages.po`)
- ngx-translate JSON resources (`strings.json`)
- Flutter ARB (`strings.arb`)
- Java properties (`dictionary.properties`)

Since these files are monolingual, the keys and segments will be imported for the language that is currently selected in the translate toolbar. The Android XML format supports untranslatable files, so if you select an XML format as input, the imported messages will already have the translatable/untranslatable field set automatically.

It is recommended to first create the messages for the base language by importing the base resource file, then select a target language in the toolbar and import a localized resource file if you already have some existing translation.
