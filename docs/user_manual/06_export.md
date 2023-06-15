## Export

Once the translation if finished, MetaPhrase makes it easy to export all the messages back to a native resource file to be integrated in the localized application. It is possible to export the list of messages in the following formats:
- Android XML
- iOS stringtable
- Windows resx
- GNU gettext PO.

For Android, untranslatable messages are included only in the base resource file and are *not* replicated in localized versions. On the other hand, for the other formats, all messages are included in all versions; in the localized ones the value of the string will be the same as the base variant. This is intended, because there is no such thing as a "non-translatable" message on iOS or other resource formats.
