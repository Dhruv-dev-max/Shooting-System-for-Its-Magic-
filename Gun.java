    public float shotRange;
    public float damage;
    public float fireRate;
    public int maxBullets;
    public float reloadTime;

    public SpatialObject playerCamera;
    private float nextShotTime;
    private float timer;
    private int bulletCount;
    private float reloadTimer;
    public SUIText bullets;
    private Laser laser = new Laser();
    private LaserHit lh;

    public void start() {
        bulletCount = maxBullets;
        reloadTimer = reloadTime;
    }

    public void repeat() {
        timer += Math.bySecond();
        bullets.setText("Bullet:" + bulletCount);
        if (Input.getKey("Shot").isPressed() && timer >= nextShotTime) {
            bulletCount -= 1;
        }
        lh = laser.trace(playerCamera.getTransform().getGlobalPosition(), playerCamera.getTransform().forward(), shotRange);

        // Reload system
        if (bulletCount <= 0) {
            reloadTimer -= Math.bySecond();
            bullets.setText("Reloding:" + Math.round(reloadTimer));
            if (reloadTimer <= 0) {
                bulletCount = maxBullets;
                reloadTimer = reloadTime;
                bullets.setText("Bullet:" + bulletCount);
            }
        }

        if (Input.getKey("Shot").isPressed() && timer >= nextShotTime && bulletCount > 0) {
            nextShotTime = timer + 1f / fireRate;
            shot();
            bullets.setText("Bullet:" + bulletCount);
        }
    }

    public void shot() {
        if (lh.getObject() != null) {
            EnemyHelth enemyHelth = lh.getObject().findComponent("EnemyHelth");
            enemyHelth.Dameage(damage);
        }
    }
