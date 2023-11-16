package models;

public class CoupleID {
    public int idArtcile;
    public int idMagasin;
    
    public CoupleID() {
    }
    public CoupleID(int idArtcile, int idMagasin) {
        this.idArtcile = idArtcile;
        this.idMagasin = idMagasin;
    }
    public int getIdArtcile() {
        return idArtcile;
    }
    public void setIdArtcile(int idArtcile) {
        this.idArtcile = idArtcile;
    }
    public int getIdMagasin() {
        return idMagasin;
    }
    public void setIdMagasin(int idMagasin) {
        this.idMagasin = idMagasin;
    }
    
}
