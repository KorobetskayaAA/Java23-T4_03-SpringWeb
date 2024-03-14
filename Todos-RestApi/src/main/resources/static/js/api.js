export const api = {
  apiBaseUrl: "http://localhost:8080/",
  getUrl(path) {
    return new URL(path || "", this.apiBaseUrl);
  },
  getAll() {
    return fetch(this.getUrl("/api")).then((response) => response.json());
  },
  get(id) {
    return fetch(this.getUrl(`/api/${id}`)).then((response) => response.json());
  },
  post(todo) {
    return fetch(this.getUrl("/api"), {
      method: "POST",
      body: JSON.stringify(todo),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    }).then((response) => response.json());
  },
  put(todo) {
    return fetch(this.getUrl("/api"), {
      method: "PUT",
      body: JSON.stringify(todo),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    }).then((response) => response.json());
  },
  putReorder(todoIds) {
    return fetch(this.getUrl("/api/reorder"), {
      method: "PUT",
      body: JSON.stringify(todoIds),
      headers: {
        "Content-type": "application/json; charset=UTF-8",
      },
    }).then((response) => response.json());
  },
  delete(id) {
    return fetch(this.getUrl(`/api/${id}`), {
      method: "DELETE",
    }).then((response) => response.json());
  },
};
