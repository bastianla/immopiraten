import { ImmoPiratenFrontendPage } from './app.po';

describe('immo-piraten-frontend App', function() {
  let page: ImmoPiratenFrontendPage;

  beforeEach(() => {
    page = new ImmoPiratenFrontendPage();
  });

  it('should display message saying app works', () => {
    page.navigateTo();
    expect(page.getParagraphText()).toEqual('app works!');
  });
});
