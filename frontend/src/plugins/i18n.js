import {createI18n} from "vue-i18n";
import ru from "@/i18/ru/messages.js";
import en from "@/i18/en/messages.js";

const i18n = createI18n({
    legacy: false,
    locale: 'en',
    fallbackLocale: 'ru',
    messages: {
        ru: {
            ...ru
        },
        en: {
            ...en
        },
    },
    dateTimeFormats: {
        'en': {
            short: {
                year: 'numeric',
                month: 'numeric',
                day: 'numeric',
            },
            long: {
                year: 'numeric',
                month: 'long',
                day: 'numeric',
                weekday: 'long',
                hour: 'numeric',
                minute: 'numeric',
            },
        },
    },
})

export default i18n;
