package app.controllers;

//Interface så jeg kan bruge metoderne til HotelRoomController, C-typen skal være Context fra javalin
public interface IC <C>{
    void create(C c);
    void  update(C c);
    void delete(C c);
    void getAll(C c);
    void getById(C c);
}
