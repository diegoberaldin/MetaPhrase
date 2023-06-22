# MetaPhrase

An open source translation editor specifically designed having in mind the issues localizers of native mobile apps (Android and iOS apps) have to deal with in their everyday tasks.

After the translation is complete, the files can be exported backwards to native formats (`.xml` or `.strings`) for each individual language so that translators can work to one language at a time.

Placeholders, for which the standard is the Android format in the editor, are converted back to iOS conventions when exporting to a stringtable.

## Rationale

MetaPhrase is designed to work with the standard localization formats used for mobile application development: string tables (aka `Localizable.strings`) files on iOS and XML resource files on Android. It is specifically designed to import and export to those formats and to manage the properties allowed by theser formats (e.g. marking some messages as untranslatable).

Special care has been taken to provide only the information needed for the translation task without cluttering the UI with too much information (e.g. not showing local variants for other languages) but at the same time all that is really needed, such as keys for context and source messages for reference.

If you want to know more about the design choices of the app and a comparison with the existing alternatives, please check out the [project homepage](https://diegoberaldin.github.io/MetaPhrase/index).

If you are interested in an in-depth description of the features, please refer to the [user manual](https://diegoberaldin.github.io/MetaPhrase/user_manual/main).

## Main features

The application main screen is the translation editor, where localizers can insert and review the translation of each message.

<div align="center">
  <table>
    <tr>
      <td><img width="900" alt="main_language" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/c8fd14e4-779d-4c6c-bf20-9faa3a0b0dd8" /></td>
    </tr>
    <tr>
      <td><img width="900" alt="local_version" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/58c5cd1d-8bab-45a3-af99-0db7e4393634" /></td>
    </tr>
  </table>
</div>

The bottom part of the main screen contains an expandable panel which gives access to useful features to make it easier to deliver high quality work for localizers such as:
- fuzzy matches in translation memory
- placeholder validation
- browse translation memory content
- glossary
- machine translation

<div align="center">
  <img width="900" alt="validation" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/5b670105-60b5-44e1-bc60-3c45b747a740">
</div>

Currently supported files:
- Android XML
- iOS stringtables
- Windows resx
- angular-translate JSON
- Flutter ARB
- Gettext PO
- Java properties

## Technology

If you are a developer and would like to know more about the technologies involved, here is a short list:

- Kotlin (multiplatform)
- Jetpack Compose
- Decompose
- Koin
- H2 DB
- JetBrains Exposed
- Redundent XML
- AndroidX datastore (preference based)
- Ktor client
- sl4j + log4j

More on this in the [tech notes](https://diegoberaldin.github.io/MetaPhrase/tech_manual/main) section of the project homepage.
