import axios from 'axios'

const config = { 
  baseURL: import.meta.env.VITE_APP_GATEWAY_URL,
  withCredentials: true 
}

export const GET = async (url, params) => {
  try {
    const conf = {...config, method: 'GET', url, params}
    const response = await axios(conf)
    return response.data
  } catch (err) {
    console.error(err)
    return {status: false}
  }
}

export const POST = async (url, data) => {
  try {
    const response = await axios.post(url, data, config)
    return response.data
  } catch (err) {
    console.error(err)
    return {status: false}
  }
}

export const PUT = async (url, data) => {
  try {
    const response = await axios.put(url, data, config)
    return response.data
  } catch (err) {
    console.error(err)
    return {status: false}
  }
}

export const PATCH = async (url, data) => {
  try {
    const response = await axios.patch(url, data, config)
    return response.data
  } catch (err) {
    console.error(err)
    return {status: false}
  }
}

export const DELETE = async (url) => {
  try {
    const response = await axios.delete(url, config)
    return response.data
  } catch (err) {
    console.error(err)
    return {status: false}
  }
}
