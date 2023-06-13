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
      <td><img width="900" alt="main_language" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/f7c3a821-3904-4f24-9c52-69806b1ecb1b" /></td>
    </tr>
    <tr>
      <td><img width="900" alt="local_version" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/1ca3c6c0-ef06-447d-ae1f-e43c2e9c944e" /></td>
    </tr>
  </table>
</div>

The bottom part of the main screen contains an expandable panel which gives access to useful features to make it easier to deliver high quality work for localizers such as:
- fuzzy matches in translation memory
- glossary
- placeholder validation

<div align="center">
  <img width="900" alt="validation" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/b5188272-4aef-4e75-84ee-51a92cd5ec83">
</div>

## Tech notes

If you are a developer and would like to know more about the technologies involved in the project, here is a short list:

- Kotlin multiplatform (JVM flavour)
- Compose multiplatform for UI
- Decompose for architecture
- Koin for DI
- H2 database as main storage
- JetBrains Exposed as ORM
- Redundent for XML generation
- sl4j and log4j for logging to console and to file
- AndroidX datastore (preference based) as secondary storage

This is a Gradle project, so please use Gradle to compile and run it. Make sure you have a JDK >= 18.

## What's next

With the main tranlation UI, message filtering, fuzzy matching in translation memory and placeholder validation, all the base features of the application have been completed. I am not evaluating new features unless I receive explicit requests by users, but I am prioriting stability issues and bug reports now.
