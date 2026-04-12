import "vuetify/styles";
import { createVuetify } from "vuetify";
import { aliases, mdi } from "vuetify/iconsets/mdi";

export default createVuetify({
  theme: {
    defaultTheme: "fleetDark",
    themes: {
      fleetDark: {
        dark: true,
        colors: {
          background: "#0f172a",
          surface: "#111c2c",
          "surface-variant": "#162536",
          primary: "#26a69a",
          secondary: "#64748b",
          error: "#ef4444",
          info: "#29b6f6",
          success: "#66bb6a",
          warning: "#ffa726",
        },
      },
    },
  },
  defaults: {
    global: {
      density: "compact",
    },
    VCard: {
      variant: "outlined",
      rounded: "md",
    },
    VSheet: {
      border: true,
      rounded: "md",
    },
    VTextField: {
      variant: "outlined",
      density: "compact",
      hideDetails: "auto",
    },
    VSelect: {
      variant: "outlined",
      density: "compact",
      hideDetails: "auto",
    },
    VAutocomplete: {
      variant: "outlined",
      density: "compact",
      hideDetails: "auto",
    },
    VCombobox: {
      variant: "outlined",
      density: "compact",
      hideDetails: "auto",
    },
    VTextarea: {
      variant: "outlined",
      density: "compact",
      hideDetails: "auto",
    },
    VBtn: {
      variant: "flat",
      rounded: "md",
    },
    VList: {
      density: "compact",
    },
    VNavigationDrawer: {
      elevation: 0,
    },
    VAppBar: {
      elevation: 0,
      border: true,
    },
    VDataTable: {
      density: "compact",
    },
  },
  icons: {
    defaultSet: "mdi",
    aliases,
    sets: {
      mdi,
    },
  },
});
