<#ftl output_format="plainText">
${msg("impersonationNotificationBody", user.firstName, user.lastName, user.username, event.date?string("MM.dd.yyyy"), event.date?string("HH:mm"), impersonator.firstName, impersonator.lastName, impersonator.username)}
