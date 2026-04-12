# Fleet Overview

Monorepo: Spring Boot **backend** va Vue **frontend**.

## Papkalar

| Katalog | Tavsif |
|---------|--------|
| `backend/` | REST API |
| `frontend/` | Asosiy veb-ilova (Vite + Vue + Vuetify) |
| `frontend-vuetify/` | **`frontend` bilan bir xil kod** — to‘liq kloni; dev port **5174** |

`frontend` va `frontend-vuetify` funksionallik jihatdan bir xil; parallel rivojlantirish yoki sinov uchun alohida ishga tushirish mumkin.

Batafsil: [`docs/PROJECT_STRUCTURE.md`](docs/PROJECT_STRUCTURE.md).

## Tez boshlash

**Backend** — `backend/` (Maven).

**Frontend (asl):**

```bash
cd frontend && npm install && npm run dev
# → http://localhost:5173
```

**Frontend (klon):**

```bash
cd frontend-vuetify && npm install && npm run dev
# → http://localhost:5174
```
