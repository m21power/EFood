sdocker:
  sudo sysctl -w kernel.apparmor_restrict_unprivileged_userns=0
startdocker: sdocker
  systemctl --user start docker-desktop
