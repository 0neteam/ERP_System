import { Routes, Route } from "react-router-dom"
import Create from '@pages/item/Create.jsx'
import List from '@pages/item/List.jsx'
import Detail from '@pages/item/Detail.jsx'


const Item = () => {
  return (
    <Routes>
      <Route path="/create" element={<Create />} />
      <Route path="/:id" element={<Detail />} />

      <Route path="*" element={<List />} />
    </Routes>
  )
}

export default Item