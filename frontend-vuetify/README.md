# frontend-vuetify

Bu katalog **`frontend/` bilan bir xil kod bazasining to‘liq nusxasi** — marshrutlar, Pinia store’lar, API (`axios`), i18n, barcha komponentlar va Vuetify UI.

## Nima uchun alohida papka?

- Asosiy ilova (`frontend/`) bilan **parallel** rivojlantirish yoki sinov.
- Bir vaqtning o‘zida ikkalasini ishga tushirish: bu yerda dev server **5174** portida (asl frontend **5173**).

Kod yangilanishini sinxron saqlash: o‘zgarishlarni ikkala tomonga `rsync` / qo‘lda merge qilish yoki faqat bitta papkada ishlash va keyin nusxa ko‘chirish.

## Ishga tushirish

```bash
npm install
npm run dev
```

Brauzer: `http://localhost:5174`

## Backend

`.env` yoki `src/plugins/axios.js` dagi `baseURL` ni Spring Boot API ga moslang (asl `frontend` bilan bir xil).

## Texnologiyalar

Vite 6, Vue 3, Vuetify 3, Pinia, Vue Router, vue-i18n, Axios, @vuepic/vue-datepicker — `frontend/package.json` bilan mos.
