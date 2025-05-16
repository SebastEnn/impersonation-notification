<#ftl output_format="plainText">
${msg("impersonationNotificationBody", user.firstName, user.lastName, user.username, event.date?string("yyyy-MM-dd"), event.date?string("HH:mm"), impersonator.firstName, impersonator.lastName, impersonator.username)}
