# Keycloak Impersonation Notification SPI

This is a Keycloak Service Provider Interface (SPI) that sends email notifications to users when their account is impersonated by an administrator or another user. This helps improve security awareness and transparency in your Keycloak instance.

## Features

- Automatically detects impersonation events in Keycloak
- Sends email notifications to impersonated users
- Includes details about who performed the impersonation and when
- Supports both HTML and plain text email formats

## Prerequisites

- Java 21 or higher
- Maven 3.8.1 or higher
- Keycloak 26.2.4 or compatible version

## Building the Project

To build the project, run the following Maven command from the project root directory:

```bash
mvn clean install
```

This will compile the code and create a JAR file in the `target` directory named `impersonation-notification-1.0.1.jar`.

## Installation

### Installing the JAR

1. Copy the generated JAR file from the `target` directory to the Keycloak providers directory:

   ```bash
   cp target/impersonation-notification-1.0.1.jar /path/to/keycloak/providers/
   ```

   Note: The exact path depends on your Keycloak installation. For a standard installation, this might be:
   - `/opt/keycloak/providers/` (standalone server)
   - `/opt/jboss/keycloak/standalone/deployments/` (older versions or JBoss-based installations)

2. Restart Keycloak to load the new provider.

### Installing the Theme

1. Copy the theme directory to the Keycloak themes directory:

   ```bash
   cp -r theme_impersonation_notification /path/to/keycloak/themes/
   ```

   Note: The exact path depends on your Keycloak installation. For a standard installation, this might be:
   - `/opt/keycloak/themes/` (standalone server)
   - `/opt/jboss/keycloak/themes/` (older versions or JBoss-based installations)

2. Restart Keycloak to load the new theme.

## Configuration

1. Log in to the Keycloak Admin Console.

2. Select the realm where you want to enable the impersonation notification.

3. Go to **Realm Settings** > **Events**.

4. Click on the **Config** tab.

5. In the **Event Listeners** field, add `impersonation-notification` to the list of event listeners.

6. Click **Save**.

7. Make sure email settings are properly configured for your realm:
   - Go to **Realm Settings** > **Email**
   - Configure the SMTP server settings
   - Save the configuration

8. (Optional) To use the custom email theme:
   - Go to **Realm Settings** > **Themes**
   - Select `theme_impersonation_notification` from the **Email Theme** dropdown
   - Click **Save**

## How It Works

When an administrator or authorized user impersonates another user in Keycloak:

1. The SPI detects the impersonation event
2. It retrieves information about both the impersonator and the impersonated user
3. It sends an email to the impersonated user with details about the impersonation
4. The email includes:
   - The name and username of the impersonated user
   - The date and time of the impersonation
   - The name and username of the impersonator

## Customization

You can customize the email templates and messages by modifying the files in the `theme_impersonation_notification` directory:

- `email/html/impersonation-notification.ftl`: HTML email template
- `email/text/impersonation-notification.ftl`: Plain text email template
- `email/messages/messages_en.properties`: Email message texts

After modifying these files, you'll need to rebuild the theme and reinstall it.

## Troubleshooting

- If emails are not being sent, check the Keycloak server logs for any error messages.
- Verify that the email configuration in your realm is correct.
- Make sure the impersonated user has a verified email address in their account.
- Check that the event listener is properly registered in the realm configuration.

## License

This project is open source and available under [The Unlicense](https://unlicense.org).
