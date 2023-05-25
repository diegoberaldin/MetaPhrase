# MetaPhrase
An open source translation editor specifically designed having in mind the issues localizators of native mobile apps (Android and iOS) have to deal with in their everyday tasks. MetaPhrase makes easier to manage translations since the base message is always displayed in local versions as a reference, and it is updated if the base version is changed at any time. After translation is complete, the files can be exported backwards to native formats (XML or .strings) for each language. The placeholders, for which the standard is the Android format in the editor, are converted back to iOS "@" conventions.

## Rationale

MetaPhrase is designed to work with the standard localization formats used for mobile application development: string tables (Localizable.strings) files on iOS and XML reource files on Android. It is specifically designed to import and export to those formats and to manage the message properties (e.g. marking some messages as untranslatable) that these formats allow.

## Main features

### Create project

![Screenshot 2023-05-25 at 08 32 55](https://github.com/diegoberaldin/MetaPhrase/assets/2738294/4d98ec74-9d0a-4493-94bb-81c796c5b0b8)

After creating a project (and selecting the relevant languages, each project must have at least one language and exactly one base language), the application allows to import Android XML resource files and/or iOS stringtables.

### Translation screen

Base languagge

![Screenshot 2023-05-25 at 08 17 57](https://github.com/diegoberaldin/MetaPhrase/assets/2738294/5ef948e4-b1cc-46e2-9344-47b3cf79cb4a)

Localized version

![Screenshot 2023-05-25 at 08 17 49](https://github.com/diegoberaldin/MetaPhrase/assets/2738294/ce1dac05-891a-4812-a391-b01fa5f2372f)

### Create new message

![Screenshot 2023-05-25 at 08 18 25](https://github.com/diegoberaldin/MetaPhrase/assets/2738294/b82407e9-12cc-442c-a582-01ba36231c78)

### Statistics

![Screenshot 2023-05-25 at 08 18 42](https://github.com/diegoberaldin/MetaPhrase/assets/2738294/16031e56-4922-4afc-84d8-09f9afff506b)

### Settings

![Screenshot 2023-05-25 at 08 19 32](https://github.com/diegoberaldin/MetaPhrase/assets/2738294/e0e1d69c-753b-46c5-9002-92ecd6b174da)

## Trivia

The name "MetaPhrase" comes from the Greek verb μεταφράζω meaning "to translate". It is part of a broader family of applications, if you are interested please have a look also to [MetaTerm](https://github.com/diegoberaldin/MetaTerm) for terminology management and [MetaLine](https://github.com/diegoberaldin/MetaLine) for text alignment.

## What's next

This is still under heavy development, I'm planning to add translation memory feature (both intra-project and inter-project, with TMX import).
