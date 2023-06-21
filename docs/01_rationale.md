# Rationale

## What's in a name?

In general, one of the most anecdotally difficult tasks in computer science is "naming things". The name of this application comes from the Greek verb μεταφράζω meaning "to translate" Etymologically, the μετα- prefix implies both the ideas of "after" and "beyond", which is accurate for a translation since translations are not only expressed "on the other side" (across a linguistic barrier) but also "after" the original text has been formulated. 

In the domain of open source tools for translators, this prefix is shared in a broader family of applications – such as [MetaTerm](https://github.com/diegoberaldin/MetaTerm) for terminology management and [MetaLine](https://github.com/diegoberaldin/MetaLine) for bilingual corpora alignment.

## Scope definition

Mobile app localization is a subtype of software localization and shares many of its constraints and pitfalls. In the first place, for example, the strings to be translated are relatively short messages used in some part of the user interface, thus appearing rather in isolation and non-contextualized.

In the second place, there are some length limitations, since messages are not bound to appear printed on paper or on a web – site  where  there are virtually no space constraints –  but in UI elements (buttons, tab bars, segmented controls, labels, etc.) where the amount of available space is limited.

In the third place some parts of the messages used in UI elements my contain varying parts that are only known during the program execution and are substituted (or rather "interpolated") at runtime, so they are marked with some special *format specifiers* which indicate the type of value (string, decimal number, floating point number, and so on…).

Finally messages should be adapted for different plural forms depending on the structure of the target language and mobile SDKs provide mechanisms to select the appropriate form of the message depending on the value which is, as in the previous case, only known at runtime.

On top of all of that, if a development team is maintaining different codebases (e.g. an Android native app and an iOS native app) and they want to use the same set of strings for both, another problem arises since the two platforms use different resource file formats which are incompatible, even format specifiers have different forms (e.g. `%s` for strings on Android whereas in the iOS world `%@` is much widely spread for any arbitrary object having a description selector).

Even if the same keys are used to reference the string resources, adding a new language in both platforms or updating a translation (either incrementally adding new strings or changing existing ones) can become extremely painful and error-prone.

## Current state of the market

 How does it compare with existing alternatives, especially in the field of mobile application development?

- in **Xcode**, localizable files are are treated as text files and they are only shown one at a time. This implies for each message only the key is displayed, which makes it difficult to work on it as a translator because the reference (source) messages are not displayed any more after a translation has been inserted. MetaPhrase solves this issue by always showing both the key and the source variant of each message.

- in **Android Studio**, if editing the XML resource directly the problem is the same as Xcode but there is a translation editor designed specifically for string resources. In this case, all the languages are displayed at the same time making it difficult to keep focus during translation if the project contains a large number of languages (e.g. it may happen to edit the wrong column by mistake). Special care has been taken in MetaPhrase to provide only the information needed without cluttering the UI with too much information (e.g. local variants for other languages are not included).

- **OmegaT**, another Java-based translation editor very well known in the open source world (and one of the most complete solutions out there) supports Android resoures but not iOS stringtables (which are supported in MetaPhrase).

- there are several open source translation tools out there, the most known are **Virtaal** (GTK-based) and **Lokalize** (Qt-based). Unfortunately, as far as software localization is concerned, they are PO-centric (PO being the standard format for messages used by GNU gettext and similar popular toolkits in open source development) and having to convert back and forth to PO from string tables and XML resources is not so straightforward. MetaPhrase provide seamless integration with XML and stringtable formats allowing to import from and export to each of them by design.

- there are many popular online platforms for localization which leverage a crowdsourcing approach and are used to have users themselves localize the applications they use. This is an interesting approach because it allows to save money (and time) but often strings are displayed without any context (not even message keys), there is no glossary/translation memory and there is no control about how the collected data are being used by the third party platform. Some servers can be hosted on a local machine thus allowing full control on the data (e.g. **Pootle**) but they require a remote server to be run and may be out of scope for small mobile projects.

In any case, one of the main strength point of MetaPhrase is that it *abstracts away* the resource file structure and allows to reason in terms of message "keys" and message "local variants", allowing to generate resource files merely as an exchange format. This makes it easier to manage multilingual projects over time (when different incremental versions of the localized application are released by maintainers).
