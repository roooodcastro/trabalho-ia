% fatos

categoria(formula_1).
categoria(formula_indy).
categoria(nascar).

equipe(ferrari).
equipe(rbr_racing).
equipe(mc_laren).
equipe(mercedes).
equipe(hendrick_motorsport).
equipe(steward_haas_racing).
equipe(kv_racing).
equipe(team_penske).

piloto(mark_webber).
piloto(sebastian_vettel).
piloto(fernando_alonso).
piloto(felipe_massa).
piloto(lewis_hamilton).
piloto(jenson_button).
piloto(nico_rosberg).
piloto(michael_schumacher).
piloto(jeff_gordon).
piloto(danica_patrick).
piloto(kyle_bush).
piloto(dale_earnhardt_jr).
piloto(ryan_newman).
piloto(kasey_kahne).
piloto(tony_kanaan).
piloto(helio_castroneves).
piloto(takuma_sato).
piloto(jimmie_johnson).

participacao(ferrari, formula_1).
participacao(rbr_racing, formula_1).
participacao(mercedes, formula_1).
participacao(hendrick_motorsport, nascar).
participacao(steward_haas_racing, nascar).
participacao(kv_racing, formula_indy).
participacao(team_penske, formula_indy).

pilota_para(sebastian_vettel, rbr_racing).
pilota_para(fernando_alonso, ferrari).
pilota_para(felipe_massa, ferrari).
pilota_para(dale_earnhardt_jr, hendrick_motorsport).
pilota_para(tony_kanaan, kv_racing).
pilota_para(michael_shumacher, mercedes).
pilota_para(jeff_gordon, hendrick_motorsport).
pilota_para(nico_rosberg, mercedes).
pilota_para(ryan_newman, steward_haas_racing).
pilota_para(danica_patrick, steward_haas_racing).
pilota_para(helio_castroneves, team_penske).
pilota_para(mark_webber, rbr_racing).

mesma_equipe(X, Y) :- piloto(X), piloto(Y), pilota_para(X, Equipe), pilota_para(Y, Equipe), X \== Y.
mesma_categoria(X, Y) :- piloto(X), piloto(Y), pilota_para(X, Equipe_a), pilota_para(Y, Equipe_b), participacao(Equipe_a, Cat), participacao(Equipe_b, Cat), X \= Y.

piloto_de_nascar(X) :- piloto(X), pilota_para(X, Equipe), participacao(Equipe, nascar).
piloto_de_f1(X) :- piloto(X), pilota_para(X, Equipe), participacao(Equipe, formula_1).

% questões

?- piloto_de_f1(felipe_massa).
?- mesma_categoria(mark_webber, michael_schumacher).
?- mesma_categoria(danica_patrick, X).
?- mesma_equipe(fernando_alonso, felipe_massa).
