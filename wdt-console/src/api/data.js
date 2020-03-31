import axiosBak from '@/libs/api.request'

export const getTableData = () => {
  return axiosBak.request({
    url: 'get_table_data',
    method: 'get'
  })
}

export const getDragList = () => {
  return axiosBak.request({
    url: 'get_drag_list',
    method: 'get'
  })
}
