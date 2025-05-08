import { Routes, Route } from "react-router-dom"
import List from '@pages/stock/List.jsx'
import Detail from '@pages/stock/Detail.jsx'

const Stock = () => {
  return (
    <Routes>
      <Route path="/:id" element={<Detail />} />

      <Route path="*" element={<List />} />
    </Routes>
  )
}

export default Stock