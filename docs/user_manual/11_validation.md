## Validation

Placeholder validation can be activated either via the translation toolbar or with the "Message" > "Validate placeholders" menu item. When this function is activated, the panel is populated with a list of all the messages that contain validation errors, i.e. mismatches between the format specifiers appearing in the source and target messages.

![validation](images/panel_validation_placeholder_full.png)

By clicking on each row, the editor will scroll to the corresponding message to fix the problem. Invalid placeholders will lead the application runtime failure for the users, so localizers must be extremely careful of them.
