<#import "template.ftl" as layout>
<@layout.emailLayout>
${kcSanitize(msg("impersonationNotificationBodyHtml", user.firstName, user.lastName, user.username, event.date?string("MM.dd.yyyy"), event.date?string("HH:mm"), impersonator.firstName, impersonator.lastName, impersonator.username))?no_esc}
</@layout.emailLayout>
