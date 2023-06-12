## Export

Once the translation if finished, MetaPhrase makes it easy to export all the messages back to a native resource file to be integrated in the localized application. It is possible to export the list of messages either in Android XML or in iOS stringtable.

For Android, untranslatable messages are included only in the base resource file and are *not* replicated in localized versions. On the other hand, for iOS, all messages are included in all versions; in the localized ones the value of the string will be the same as the base variant. This is intended, since in stringtables all messages must be replicated and there is no such thing as a "non-translatable" key.
