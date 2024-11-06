/** @type {import('tailwindcss').Config} */
module.exports = {
  content: ["./src/**/*.{html,js}"],
  theme: {
    extend: {
      colors: {
        primary: "rgb(160,62,227)",
        secondary: "#B4D4FF",
        third: "#86B6F6",
        fourth: "#176B87",
        button: "rgb(59,130,246)",
        border: "rgb(223, 221, 221)",
      },
      fontFamily: {
        dmserif: ["DM Serif", "serif"],
        com: ["Comfortaa", "cursive"],
      },
    },
  },
  plugins: [],
};
