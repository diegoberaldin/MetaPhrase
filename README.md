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
  <img src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/41553c7a-1ce8-41bd-adb4-1c5016f4bd70" />
</p>

#### Project list

<p align="center">
  <img src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/fe286862-1555-41fb-bc67-4e0b83abb708" />
</p>

### Create project

<p align="center">
  <img src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/4d98ec74-9d0a-4493-94bb-81c796c5b0b8" />
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
  <img src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/5ef948e4-b1cc-46e2-9344-47b3cf79cb4a" />
</p>

For the base (or default) language, messages can be edited and updated in their source version. Additionally, using the
lock/unlock button, it is possible to mark messages as translatable or untranslatable. Untranslatable messages will not
appear in other languages.

#### Localized version

<p align="center">
  <img src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/ce1dac05-891a-4812-a391-b01fa5f2372f" />
</p>

For non-base languages, messages are edited in their localized version. The base version is displayed as the source text
and the key is always shown as well.

### Create new message

<p align="center">
  <img src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/b82407e9-12cc-442c-a582-01ba36231c78" />
</p>

The application allows to add new messages providing the key and a text. The text inserted as a value will be of the
current opened languages (and new messages are automatically added to all languages as with the same key and an empty
value).

### Statistics

<p align="center">
  <img src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/16031e56-4922-4afc-84d8-09f9afff506b" />
</p>

This dialog shows some statistics about the current project. The completion rate for each language does not account the
untranslatable messages.

### Settings

<p align="center">
  <img src="https://github.com/diegoberaldin/MetaPhrase/assets/2738294/e0e1d69c-753b-46c5-9002-92ecd6b174da" />
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

This is still under heavy development, I'm planning to add translation memory feature (both intra-project and
inter-project, with TMX import).
