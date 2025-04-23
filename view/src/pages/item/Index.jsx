import { Routes, Route } from "react-router-dom"
import Create from '@pages/item/Create.jsx'


const Item = () => {
  return (
    <Routes>
      <Route path="/create" element={<Create />} />

    </Routes>
  )
}

export default Item