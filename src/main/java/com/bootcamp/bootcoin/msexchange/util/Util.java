package com.bootcamp.bootcoin.msexchange.util;

import com.bootcamp.client.personal.entity.DocumentType;
import com.bootcamp.client.personal.entity.PersonalDocument;
import com.bootcamp.client.util.handler.exceptions.BadRequestException;

import java.util.Currency;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Util {

    public static boolean isValidCurrency(String code){
        try {
            Currency c = Currency.getInstance(code);
            if (c != null) return true;
        } catch (Exception e) {
            return false;
        }
        return false;
    }

    public static void log(Class context_class, Level level, String message, Object[] params) {
        /**
         * ERROR – something terribly wrong had happened, that must be investigated immediately. No system can tolerate items logged on this level. Example: NPE, database unavailable, mission critical use case cannot be continued.
         *
         * WARN – the process might be continued, but take extra caution. Actually I always wanted to have two levels here: one for obvious problems where work-around exists (for example: “Current data unavailable, using cached values”) and second (name it: ATTENTION) for potential problems and suggestions. Example: “Application running in development mode” or “Administration console is not secured with a password”. The application can tolerate warning messages, but they should always be justified and examined.
         *
         * INFO – Important business process has finished. In ideal world, administrator or advanced user should be able to understand INFO messages and quickly find out what the application is doing. For example if an application is all about booking airplane tickets, there should be only one INFO statement per each ticket saying “[Who] booked ticket from [Where] to [Where]“. Other definition of INFO message: each action that changes the state of the application significantly (database update, external system request).
         *
         * DEBUG – Developers stuff. I will discuss later what sort of information deserves to be logged.
         *
         * TRACE – Very detailed information, intended only for development. You might keep trace messages for a short period of time after deployment on production environment, but treat these log statements as temporary, that should or might be turned-off eventually. The distinction between DEBUG and TRACE is the most difficult, but if you put logging statement and remove it after the feature has been developed and tested, it should probably be on TRACE level.
         */
        Logger logger = Logger.getLogger(String.valueOf(context_class));
        logger.log(level, message, params);
    }

    public static void log(Class context_class, Level level, String message) {
        log(context_class, level, message, null);
    }

    public static void verifyCurrency(String currencyCode, Class context_class) {
        if(!Util.isValidCurrency(currencyCode)){
            throw new BadRequestException(
                    "CURRENCY",
                    "[CURRENCY] "+currencyCode + " is an invalid currency code.",
                    "",
                    context_class,
                    "update"
            );
        }
    }

    public static boolean verifyRuc(String ruc, String ruc_to_compare, Class context_class, String context) {

        Util.isNumber(ruc, "RUC", context_class, context);

        Util.verifyLength(ruc, 11, "RUC", context_class, context);

        if(!Objects.equals(ruc, ruc_to_compare)) {
            throw new BadRequestException(
                    "RUC",
                    "["+context+"] An item with the RUC " + ruc + " was not found. >> ",
                    "An error occurred while trying to create an item.",
                    context_class,
                    context
            );
        }

        return true;
    }

    public static void verifyLength(String ruc, int length, String type, Class context_class, String context) {
        if(ruc.length() != length) {
            throw new BadRequestException(
                    type.toUpperCase(),
                    "["+context+"] The "+type+" length ("+ruc.length()+ ") should be "+length+" digits.",
                    "An error occurred while trying to create an item.",
                    context_class,
                    context
            );
        }
    }

    public static void isNumber(String n, String type, Class context_class, String context) {
        try {
            Long.parseLong(n);

        } catch (Exception e){
            throw new BadRequestException(
                    type.toUpperCase(),
                    "["+context+"] The "+type+" ("+n+ ") should be a number.",
                    "An error occurred while trying to create an item.",
                    context_class,
                    context
            );
        }
    }

    public static void verifyDocumentNumber(
            String documentType,
            String documentNumber,
            Class context_class,
            String context
    ) {

        DocumentType doc;

        try {

            doc = PersonalDocument.getInstance().getDocumentTypes().get(documentType);
            if(doc == null) throw new Exception("DocumentType is not valid");

        } catch (Exception e) {
            throw new BadRequestException(
                    "DocumentType",
                    "["+context+"] The document type <<"+documentType+ ">> is not valid.",
                    "An error occurred while trying to create an item.",
                    context_class,
                    context
            );
        }

        if(!((documentNumber.length() >= doc.getMinLength()) && (documentNumber.length() <= doc.getMaxLength()))) {
            throw new BadRequestException(
                    "DocumentNumber",
                    "["+context+"] The document number length ("+documentNumber.length()+ ") should be between "+doc.getMinLength()+" and "+doc.getMaxLength()+" digits.",
                    "An error occurred while trying to create an item.",
                    context_class,
                    context
            );
        }

    }

}
