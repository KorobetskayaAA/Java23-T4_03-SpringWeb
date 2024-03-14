import { api } from "./api.js";
import { components } from "./components.js";

function Todo(text, created, done) {
  this.text = text;
  this.created = created || new Date();
  this.done = !!done;
}

window.addEventListener("load", () => {
  components.todoAddForm.addEventListener("submit", (ev) => {
    ev.preventDefault();
    ev.stopPropagation();
    const form = ev.target;
    const newTodo = new Todo(form.elements.text.value);
    addTodo(newTodo);
    form.reset();
  });

  loadData();

  function loadData() {
    api
      .getAll()
      .then((data) => {
        components.setLoading(true);
        components.hideAlert();
        components.todoListContainer.replaceChildren(
          components.createTodoList(data, editTodo, removeTodo, reorderTodos)
        );
      })
      .catch((err) => {
        console.error(err);
        components.showAlert("Ошибка загрузки списка задач");
      })
      .finally(() => components.setLoading(false));
  }

  function addTodo(todo) {
    withReloadOnFetch(api.post(todo), "Ошибка создания задачи");
  }

  function removeTodo(todo) {
    withReloadOnFetch(api.delete(todo.id), "Ошибка удаления задачи");
  }

  function editTodo(todo) {
    withReloadOnFetch(api.put(todo), "Ошибка сохранения задачи");
  }

  function reorderTodos() {
    let reorderedIds = components.getOrderedTodos().map((todo) => todo.id);
    withReloadOnFetch(
      api.putReorder(reorderedIds),
      "Ошибка сохранения порядка задач"
    );
  }

  function withReloadOnFetch(promise, errorMessage) {
    promise
      .then(() => {
        loadData();
      })
      .catch((err) => {
        console.error(err);
        components.showAlert(errorMessage);
      });
  }
});
