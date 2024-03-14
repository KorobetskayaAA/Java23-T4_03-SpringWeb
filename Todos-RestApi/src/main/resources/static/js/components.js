export const components = {
  todoAddForm: document.getElementById("todoList-form"),
  todoListContainer: document.getElementById("todoList-container"),
  loadingSpinner: document.getElementById("loading"),
  alert: document.getElementById("alert"),

  getOrderedTodos() {
    return [...document.getElementsByClassName("todo")].map(
      (elem) => elem.todo
    );
  },

  createTodoList(todos, onEditTodo, onRemoveTodo, onMoveTodo) {
    let todoList = document.createElement("ol");
    todoList.id = "todoList";
    todoList.classList.add("list-group");
    todoList.classList.add("list-group-numbered");

    fillTodoListItems(todoList);

    return todoList;

    function fillTodoListItems(todoList) {
      if (!todoList) todoList = document.getElementById("todoList");
      for (let todo of todos) {
        todoList.append(createTodoListItem(todo));
      }
      if (todoList.children.length > 0) {
        todoList.firstChild.querySelector(".todo-button-up").disabled = true;
        todoList.lastChild.querySelector(".todo-button-down").disabled = true;
      }
    }
    function createTodoListItem(todo) {
      let todoListItem = document.createElement("li");
      todoListItem.classList.add("todo");
      todoListItem.classList.add("list-group-item");

      todoListItem.append(createTodoListItemDone());
      todoListItem.append(createTodoListItemText());
      todoListItem.append(createTodoListItemsControls(todoListItem));
      todoListItem.append(createTodoListItemDate());

      todoListItem.todo = todo;

      makeListItemDraggable();

      return todoListItem;

      function createTodoListItemDate() {
        let todoDate = document.createElement("small");
        todoDate.classList.add("todo-created");
        todoDate.innerText = "Создано: " + todo.created.toLocaleString();
        return todoDate;
      }

      function createTodoListItemsControls() {
        let todoControls = document.createElement("div");
        todoControls.classList.add("todo-controls");
        todoControls.append(
          createIconButton("todo-button-up", "caret-up", "secondary", () => {
            withDisablingUpDownButtons(todoListItem.parentNode, () =>
              todoListItem.previousSibling.before(todoListItem)
            );
            onMoveTodo?.();
          })
        );
        todoControls.append(
          createIconButton(
            "todo-button-down",
            "caret-down",
            "secondary",
            () => {
              withDisablingUpDownButtons(todoListItem.parentNode, () =>
                todoListItem.nextSibling.after(todoListItem)
              );
              onMoveTodo?.();
            }
          )
        );
        todoControls.append(
          createIconButton("todo-button-remove", "trash-fill", "danger", () =>
            onRemoveTodo?.(todo)
          )
        );
        return todoControls;
      }

      function createTodoListItemDone() {
        let todoCheckbox = document.createElement("input");
        todoCheckbox.classList.add("todo-done");
        todoCheckbox.type = "checkbox";
        todoCheckbox.checked = todo.done;
        todoCheckbox.addEventListener("change", (ev) => {
          todo.done = ev.target.checked;
          onEditTodo(todo);
        });
        return todoCheckbox;
      }

      function createTodoListItemText() {
        let todoText = document.createElement("p");
        todoText.classList.add("todo-text");
        todoText.innerText = todo.text;
        return todoText;
      }

      function createIconButton(className, iconName, color, onClick) {
        let button = document.createElement("button");
        button.classList.add(className);
        button.classList.add("btn");
        button.classList.add("btn-outline-" + color);
        button.classList.add("btn-sm");
        button.classList.add("bi");
        button.classList.add("bi-" + iconName);
        button.addEventListener("click", onClick);
        return button;
      }

      function makeListItemDraggable() {
        todoListItem.draggable = true;

        todoListItem.addEventListener("dragstart", (evt) => {
          evt.target.classList.add("dragging");
        });
        todoListItem.addEventListener("dragend", (evt) => {
          evt.target.classList.remove("dragging");
          onMoveTodo?.();
        });
        todoListItem.addEventListener("dragover", (evt) => {
          evt.preventDefault();
          const draggingElement = todoList.querySelector(".dragging");
          const dragTarget = evt.target;
          // нельзя бросить на себя и можно перетаскивать только задачи
          if (
            draggingElement === dragTarget ||
            !dragTarget.classList.contains("todo")
          ) {
            return;
          }
          const dragTargetRect = dragTarget.getBoundingClientRect();
          const dragTargetMiddle =
            (dragTargetRect.top + dragTargetRect.bottom) / 2;
          if (evt.clientY > dragTargetMiddle) {
            withDisablingUpDownButtons(todoList, () =>
              dragTarget.after(draggingElement)
            );
          } else {
            withDisablingUpDownButtons(todoList, () =>
              dragTarget.before(draggingElement)
            );
          }
        });
      }
    }
    function withDisablingUpDownButtons(todoList, callback) {
      if (todoList.firstChild) {
        todoList.firstChild.querySelector(".todo-button-up").disabled = false;
        todoList.lastChild.querySelector(".todo-button-down").disabled = false;
      }

      callback();

      if (todoList.firstChild) {
        todoList.firstChild.querySelector(".todo-button-up").disabled = true;
        todoList.lastChild.querySelector(".todo-button-down").disabled = true;
      }
    }
  },

  setSubmitting(isSubmitting) {
    this.todoAddForm.querySelector(".spinner-border").hidden = !isSubmitting;
    this.todoAddForm.querySelector("button").disabled = isSubmitting;
  },
  setLoading(isLoading) {
    this.loadingSpinner.hidden = !isLoading;
    if (isLoading) {
      this.todoListContainer.replaceChildren();
    }
    this.todoAddForm.querySelector("button").disabled = isLoading;
  },
  hideAlert() {
    this.alert.hidden = true;
  },
  showAlert(text, color = "danger") {
    this.alert.hidden = false;
    this.alert.innerText = text;
    this.alert.className = "alert alert-" + color;
  },
};
