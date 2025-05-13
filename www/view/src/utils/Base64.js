export const decode = (param) => {
  return JSON.parse(decodeURIComponent(window.atob(param)))
}

export const encode = (param) => {
  return window.btoa(encodeURIComponent(JSON.stringify(param)))
}