import "vuetify/styles";
import { createVuetify } from "vuetify";
import { aliases, mdi } from "vuetify/iconsets/mdi";

const savedTheme = localStorage.getItem("fleet-theme") || "fleetLight";

export default createVuetify({
  theme: {
    defaultTheme: savedTheme,
    themes: {
      fleetLight: {
        dark: false,
        colors: {
          background: "#f0f4f8",
          surface: "#ffffff",
          "surface-variant": "#f1f5f9",
          primary: "#0d9488",
          secondary: "#475569",
          error: "#ef4444",
          info: "#0284c7",
          success: "#16a34a",
          warning: "#d97706",
        },
      },
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
      rounded: "lg",
    },
    VSheet: {
      border: true,
      rounded: "lg",
    },
    VTextField: {
      variant: "outlined",
      density: "comfortable",
      hideDetails: "auto",
      bgColor: "surface",
      color: "primary",
      rounded: "lg",
    },
    VSelect: {
      variant: "outlined",
      density: "comfortable",
      hideDetails: "auto",
      bgColor: "surface",
      color: "primary",
      rounded: "lg",
      menuProps: {
        scrollStrategy: "reposition",
        zIndex: 6200,
      },
    },
    VAutocomplete: {
      variant: "outlined",
      density: "comfortable",
      hideDetails: "auto",
      bgColor: "surface",
      color: "primary",
      rounded: "lg",
      menuProps: {
        scrollStrategy: "reposition",
        zIndex: 6200,
      },
    },
    VCombobox: {
      variant: "outlined",
      density: "comfortable",
      hideDetails: "auto",
      bgColor: "surface",
      color: "primary",
      rounded: "lg",
      menuProps: {
        scrollStrategy: "reposition",
        zIndex: 6200,
      },
    },
    VTextarea: {
      variant: "outlined",
      density: "comfortable",
      hideDetails: "auto",
      bgColor: "surface",
      color: "primary",
      rounded: "lg",
    },
    VCheckbox: {
      density: "comfortable",
      hideDetails: "auto",
      color: "primary",
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
    /* Custom UDialog .u-dialog-mask is z-index 2006 — overlays must sit above it */
    VOverlay: {
      zIndex: 6200,
    },
    VMenu: {
      zIndex: 6200,
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
