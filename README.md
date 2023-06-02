# MetaPhrase

An open source translation editor specifically designed having in mind the issues localizers of native mobile apps (
Android and iOS apps) have to deal with in their everyday tasks. MetaPhrase makes easy to manage translations since the
base message is always displayed in local versions as a reference.

After the translation is complete, the files can be exported backwards to native formats (`.xml` or `.strings`) for each
individual language so that translators can work to one language at a time.

Placeholders, for which the standard is the Android format in the editor, are converted back to iOS "@" conventions when
exporting to a string table.

## Rationale

MetaPhrase is designed to work with the standard localization formats used for mobile application development: string
tables (AKA `Localizable.strings`) files on iOS and XML resource files on Android. It is specifically designed to import
and export to those formats and to manage the message properties (e.g. marking some messages as untranslatable) that
these formats allow.

Special care has been taken to provide only the information needed for the translation task without cluttering the UI
with too much information (e.g. not showing local variants for other languages).

Why should anyone use MetaPhrase, how does it compare with existing alternatives?

- In Apple Xcode, localizable files are only shown one at a time, for each message only the key is displayed. This makes
  it difficult to work on it as a translator because the reference (source or base) message is not displayed.
- In Android Studio, if editing the XML resource directly the problem is the same as Xcode; if using the translation
  editor all the languages are displayed at the same time making it difficult to keep focus if the project contains a
  large number of languages (and additionally editing the wrong column).
- There are several open source translation tools out there, maybe the most known are Virtaal (GTK-based) and Lokalize (
  Qt.based). Unfortunately, as far as I've seen, as far as software localization is concerned, they are PO-centric (PO
  being the standard format for messages used by GNU gettext and similar popular toolkits in open source development).
  But having to convert back and forth to PO from string tables and XML resources is cumbersome and not so
  straightforward.

## Main features

### Intro screens

At startup, either a welcome screen is displayed or a screen with the project list to open an existing project.

#### Welcome screen

<p align="center">
  <img width="900" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/41553c7a-1ce8-41bd-adb4-1c5016f4bd70" />
</p>

#### Project list

<p align="center">
  <img width="900" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/fe286862-1555-41fb-bc67-4e0b83abb708" />
</p>

### Create project

<p align="center">
  <img width="600" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/4d98ec74-9d0a-4493-94bb-81c796c5b0b8" />
</p>

Creating a project involves selecting an internal name and the relevant languages. Each project must have at least one
language and exactly one base language which will be used as a reference for creating locale-specific versions. The base
language is marked with a flag.

### Translation screen

This is the main application screen, which allows to insert the message translation. The key of the message is always
displayed and can be included in textual queries in the "Search" box. Messages can be filtered by text but also by
category: All messages, Messages needing translation, Only translatable messages (excluding untranslatable messages).

#### Base language

<p align="center">
  <img width="900" alt="main_language" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/f7c3a821-3904-4f24-9c52-69806b1ecb1b">
</p>

For the base (or default) language, messages can be edited and updated in their source version. Additionally, using the
lock/unlock button, it is possible to mark messages as translatable or untranslatable. Untranslatable messages will not
appear in other languages.

#### Local version

<p align="center">
  <img width="900" alt="local_version" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/1ca3c6c0-ef06-447d-ae1f-e43c2e9c944e">
</p>

For non-base languages, messages are edited in their localized version. The base version is displayed as the source text
and the key is always shown as well.

### Bottom panel

The bottom part of the main screen contains an expandable panel which gives access to useful features to make it easier to deliver high quality work for localizers.

#### Fuzzy match

<p align="center">
  <img width="900" alt="fuzzy_match" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/109888f8-ac57-4f3e-950e-51547b501bd5">
</p>

This section is populated whenever a new message is being edited. It contains the fuzzy matches with a score higher or equal to the similarity threshold selected in the settings dialog. The best match can be inserted via the "Memory" > "Insert best match" menu, every other match can be copied to the target field via the copy button. This helps maintain a higher consistency in the localization project. Entries displayed here can either come from the same projet (internal matches) or can come from the translation memory. Memory is populated by importing TMX files and each project's content can be exported as a TMX.

#### Placeholder validation

<p align="center">
  <img width="900" alt="validation" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/b5188272-4aef-4e75-84ee-51a92cd5ec83">
</p>

Individual languages of each projects can be validated, i.e. the program can check whether all the messages contain valid format placeholders (matching the source language message). Errors are displayed in the panel and clicking on each result will navigate to the corresponding message in the main UI to edit and fix the problem. Invalid placeholders will lead the application runtime failure for the users, so localizers must be extremely careful of them.

#### Browsing Translation Memory

<p align="center">
  <img width="900" alt="browse_tm" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/b834ace7-4116-457e-8c26-197eef655143">
</p>

The content of the TM can be explored and used as a "concordancer". Additionally, individual entries can be deleted from this panel if they are not useful. The whole TM can be erased too, in order to populate it again with only specific TMX files. The origin of each correspondence in the TM is displayed underneath the message pair.

### Create new message

<p align="center">
  <img width="600" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/b82407e9-12cc-442c-a582-01ba36231c78" />
</p>

The application allows to add new messages providing the key and a text. The text inserted as a value will be of the
current opened languages (and new messages are automatically added to all languages as with the same key and an empty
value).

### Statistics

<p align="center">
  <img width="600" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/16031e56-4922-4afc-84d8-09f9afff506b" />
</p>

This dialog shows some statistics about the current project. The completion rate for each language does not account the
untranslatable messages.

### Settings

<p align="center">
  <img width="600" alt="settings" src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/82fcc407-f2cb-4b29-a621-2ea26b69480a">
</p>

This dialog allows to customize the application. For the time being, the UI language is the only configurable item; more
options to come in the future.

## Trivia

The name "MetaPhrase" comes from the Greek verb μεταφράζω meaning "to translate". It is part of a broader family of
applications and translation tools I'm developing: if you are interested please have a look also
to [MetaTerm](https://github.com/diegoberaldin/MetaTerm) for terminology management
and [MetaLine](https://github.com/diegoberaldin/MetaLine) for text alignment.

The technology stack is the following:
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
