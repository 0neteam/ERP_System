import { Routes, Route } from "react-router-dom"
import Create from '@pages/order/Create.jsx'
import List from '@pages/order/List.jsx'
import Detail from '@pages/order/Detail.jsx'
import Edit from '@pages/order/Edit.jsx'

const Order = () => {
  return (
    <Routes>
      <Route path="/create" element={<Create />} />
      <Route path="/edit/:id" element={<Edit />} />
      <Route path="/:id" element={<Detail />} />

      <Route path="*" element={<List />} />
    </Routes>
  )
}

export default Order