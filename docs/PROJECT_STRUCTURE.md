# Fleet Overview — loyiha tuzilmasi

## Tuzilma

```
fleet-overview/
├── backend/              # Spring Boot API
├── frontend/             # Asosiy UI (Vite + Vue + Vuetify + barcha modullar)
├── frontend-vuetify/     # `frontend/` ning to‘liq nusxasi — kod va funksionallik bir xil
├── docs/
│   └── PROJECT_STRUCTURE.md
└── README.md
```

## `frontend` va `frontend-vuetify`

- **`frontend-vuetify`** — `frontend` papkasining **to‘liq kloni** (marshrutlar, store’lar, komponentlar, axios, i18n).
- Farq faqat:
  - `package.json` ichidagi **`name`**: `frontend-vuetify`
  - **`vite.config.js`**: dev server porti **5174** (asl frontend odatda **5173**), parallel ishlatish uchun.

Yangi o‘zgarishlarni ikkala tomonga qo‘llash uchun (masalan, faqat `frontend`da ishlab, keyin nusxa ko‘chirish):

```bash
# frontend → frontend-vuetify (node_modules va dist saqlanmaydi)
rsync -a --delete --exclude node_modules --exclude dist \
  frontend/ frontend-vuetify/
```

Keyin qo‘lda tiklash:

- `frontend-vuetify/package.json` → `"name": "frontend-vuetify"`
- `frontend-vuetify/vite.config.js` → `server.port: 5174`

Yoki aksincha yo‘nalishda ham xuddi shu `rsync` (papkalarni almashtiring).

## Tez ishga tushirish

| Papka | Buyruq | URL |
|--------|--------|-----|
| `frontend/` | `npm run dev` | http://localhost:5173 |
| `frontend-vuetify/` | `npm run dev` | http://localhost:5174 |

Backend bitta bo‘lsa, ikkala frontend ham xuddi shu API ga ulanishi mumkin (`src/plugins/axios.js`).
