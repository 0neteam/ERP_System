import { defineConfig } from 'vite'
 import react from '@vitejs/plugin-react'
 import { fileURLToPath, URL } from 'url'
 
 // https://vite.dev/config/
 export default defineConfig({
   plugins: [react()],
   base: "/",
   server: {
     host: true,
   },
   resolve: {
     alias: {
       '@': fileURLToPath(new URL('./src', import.meta.url)),
       '@rt': fileURLToPath(new URL('./src/routeCheck', import.meta.url)),
     }
   },
 })