import { Routes, Route } from "react-router-dom"
import List from '@pages/transp/List.jsx'
import Detail from '@pages/transp/Detail.jsx'

const Transp = () => {
  return (
    <Routes>
      <Route path="/:id" element={<Detail />} />

      <Route path="*" element={<List />} />
    </Routes>
  )
}

export default Transp