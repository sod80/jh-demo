import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BlogBlogModule } from './blog/blog.module';
import { BlogEntryModule } from './entry/entry.module';
import { BlogTagModule } from './tag/tag.module';
import { BlogProductModule as StoreProductModule } from './store/product/product.module';
import { BlogGreetingModule as GreetingGreetingModule } from './greeting/greeting/greeting.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        BlogBlogModule,
        BlogEntryModule,
        BlogTagModule,
        StoreProductModule,
        GreetingGreetingModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogEntityModule {}
