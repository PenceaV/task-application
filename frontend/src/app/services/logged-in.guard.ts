import { CanActivateFn, Router } from '@angular/router';
import { inject } from '@angular/core';

export const loggedInGuard: CanActivateFn = (route, state) => {
  const router = inject(Router);
  if (localStorage.getItem('token') != null) {
    return true;
  }
  router.navigate(['/login']);
  return false;
};
