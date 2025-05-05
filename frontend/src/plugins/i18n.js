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
})

export default i18n;
