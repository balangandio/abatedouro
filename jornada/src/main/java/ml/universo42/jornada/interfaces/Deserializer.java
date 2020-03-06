package ml.universo42.jornada.interfaces;

public interface Deserializer<T> {

    public T deserialize(String serialData);

}
