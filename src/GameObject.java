public abstract class GameObject {
    // Constantes globais
    public static final int INACTIVE = 0;
    public static final int ACTIVE = 1;
    public static final int EXPLODING = 2;

    // Encapsulamento dos atributos
    protected double x;
    protected double y;
    protected double radius;
    protected int state;
    // Construtor para inicializar as posições de qualquer objeto
    public GameObject(double x, double y, double radius) {
        this.x = x;
        this.y = y;
        this.radius = radius;
        this.state = ACTIVE;
    }

    // Métodos abstratos por fins de polimorfismo nas classes filha
    public abstract void update(long delta);
    public abstract void draw();
    // Getters e setters:
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getRadius() {
        return radius;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    // Métodos para identificação de estados
    public boolean isActive() {
        return this.state == ACTIVE;
    }

    public boolean isExploding() {
        return this.state == EXPLODING;
    }

    public boolean isInactive() {
        return this.state == INACTIVE;
    }

    // Método para lidar com a lógica de colisão com polimorfismo
    public boolean colisaoEntre(GameObject outro, double peso){
        double dx = getX() - outro.getX();
        double dy = getY() - outro.getY();
        double dist = Math.sqrt(dx*dx + dy*dy);

        return dist < (outro.getRadius() + this.radius * peso);
    }
}