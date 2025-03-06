class Stack {
    constructor(items) {
        this.items = items ? items : [];
    }

    getAllItems() {
        return this.items;
    }

    getItems() {
        let copy = this.items.map(it => {return {...it}});
        copy.pop();
        return copy;
    }

    push(item) {
        this.items.push(item);
    }

    pop() {
        if (this.isEmpty()) {
            return "";
        }
        return this.items.pop();
    }

    // Peek operation
    peek() {
        if (this.isEmpty()) {
            return "";
        }
        return this.items[this.items.length - 1];
    }

    isEmpty() {
        return this.items.length === 0;
    }

    size() {
        return this.items.length;
    }
}

export {Stack}
