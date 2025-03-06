import {createI18n} from "vue-i18n";
import ru from "@/i18/ru/messages.js";
import uz from "@/i18/uz/messages.js";

const i18n = createI18n({
    legacy: false,
    locale: 'ru',
    fallbackLocale: 'uz',
    messages: {
        ru: {
            ...ru
        },
        uz: {
            ...uz
        },
    },
})

export default i18n;
