import { StrictMode } from 'react'
import { createRoot } from 'react-dom/client'
import './index.css'
import App from './App.tsx'
import ManagementTodoStoreProvider from './store/management-todo-store.tsx'

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ManagementTodoStoreProvider>
      <App />
    </ManagementTodoStoreProvider>
  </StrictMode>,
)
