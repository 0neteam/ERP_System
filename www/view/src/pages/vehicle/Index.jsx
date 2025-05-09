import { Routes, Route } from "react-router-dom"
import Create from '@pages/vehicle/Create.jsx'
import List from '@pages/vehicle/List.jsx'
import Detail from '@pages/vehicle/Detail.jsx'

const Vehicle = () => {
  return (
    <Routes>
      <Route path="/create" element={<Create />} />
      <Route path="/:id" element={<Detail />} />

      <Route path="*" element={<List />} />
    </Routes>
  )
}

export default Vehicle