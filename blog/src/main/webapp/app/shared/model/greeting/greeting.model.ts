export interface IGreeting {
    id?: number;
    message?: string;
}

export class Greeting implements IGreeting {
    constructor(public id?: number, public message?: string) {}
}
